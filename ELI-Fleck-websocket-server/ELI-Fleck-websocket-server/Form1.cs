using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using Fleck;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Diagnostics;


namespace ELI_Fleck_websocket_server
{
    public partial class Form1 : Form
    {
        delegate void SetTextCallback(string text);
        delegate void SetImageCallback(byte[] f);
        string saveImagePath = "imagesCache";
        string algoPath = "ALGO\\eyeWaysAlgo.exe";
        string StartupPath = Application.StartupPath;

        private ToolTip toolTip = new ToolTip();
        private bool isShown = false;

        List<IWebSocketConnection> allSockets;
        WebSocketServer server;

        public Form1()
        {
            InitializeComponent();
            setStateEnable(true);
            textBox2.Text = StartupPath + saveImagePath;
            textBox3.Text = StartupPath + algoPath;

            textBox2.Enabled = false;
            textBox3.Enabled = false;
            textBox4.Enabled = false;
        }

        private void setStateEnable(bool p)
        {
            button1.Enabled = p;
            button4.Enabled = p;
            button5.Enabled = p;
            button6.Enabled = p;
            button2.Enabled = !p;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            setStateEnable(false);
            allSockets = new List<IWebSocketConnection>();
            server = new WebSocketServer("ws://localhost:31285/");

            Thread startthread = new Thread(new ThreadStart(startWebSocketServer));
            startthread.Start();
        }

        private void startWebSocketServer()
        {
            // FleckLog.Level = LogLevel.Debug;
            server.Start(socket =>
            {
                socket.OnOpen = () =>
                {
                    allSockets.Add(socket);
                    write_toEditText("Open: user number :" + allSockets.IndexOf(socket));
                };
                socket.OnClose = () =>
                {
                    write_toEditText("Close : user number :" + allSockets.IndexOf(socket));
                    allSockets.Remove(socket);
                };
                socket.OnMessage = message =>
                {
                    sensorPacket pack = new sensorPacket();
                    imagePacket imagePack = new imagePacket();
                    try
                    {
                        pack = JsonConvert.DeserializeObject<sensorPacket>(message);
                        if (!pack.ns.Equals("org.jwebsocket.plugins.system", StringComparison.Ordinal))
                        {
                            // this is image
                            imagePack = JsonConvert.DeserializeObject<imagePacket>(message);
                            Thread t = new Thread(unused => doSomthingWithPicture(imagePack.data, imagePack.filename, socket));
                            t.Start();
                        }
                        else
                        {
                            write_toEditText("user number " + allSockets.IndexOf(socket) + " Echo: " + pack.data);
                            socket.Send("user number " + allSockets.IndexOf(socket) + " Echo: " + pack.data);
                        }
                    }
                    catch (Exception)
                    {
                        // message not in format?
                    }

                };
                socket.OnBinary = file =>
                {
                    write_toImage(file);
                };
            });
        }

        private void doSomthingWithPicture(string data, string name, IWebSocketConnection sock)
        {
            // write_toEditText("arive compress: " + Convert.FromBase64String(s).Length);
            //byte[] imgArr = Inflater.Decompress( Convert.FromBase64String(s));
            // write_toEditText("de comprees: " + imgArr.Length);
            //
            byte[] imgArr = Convert.FromBase64String(data);
            string whereToSave = saveImagePath + "/" + name;
            Image img;
            using (MemoryStream ms = new MemoryStream(imgArr))
            {
                img = Image.FromStream(ms);
            }
            Bitmap bmp1 = new Bitmap(img);
            img.Dispose();
            ImageCodecInfo jgpEncoder = GetEncoder(ImageFormat.Jpeg);
            System.Drawing.Imaging.Encoder myEncoder = System.Drawing.Imaging.Encoder.Quality;
            EncoderParameters myEncoderParameters = new EncoderParameters(1);
            EncoderParameter myEncoderParameter = new EncoderParameter(myEncoder, 100L);
            myEncoderParameters.Param[0] = myEncoderParameter;
            bmp1.Save(whereToSave, jgpEncoder, myEncoderParameters);
            bmp1.Dispose();

            var proc = new Process();
            proc.StartInfo.FileName = algoPath;
            proc.StartInfo.Arguments = saveImagePath + "/" + name;
            proc.StartInfo.UseShellExecute = false;
            proc.StartInfo.RedirectStandardOutput = true;
            proc.StartInfo.CreateNoWindow = true;
            proc.Start();
            var sw = proc.StandardOutput;
            sock.Send(sw.ReadLine());
            sw.Close();
            proc.WaitForExit();
            proc.Close();
            write_toImage(imgArr);
        }

        private void write_toImage(byte[] img)
        {
            if (pictureBox1.InvokeRequired)
            {
                SetImageCallback d = new SetImageCallback(setImage);
                this.Invoke(d, new object[] { img });
            }
            else
            {
                MemoryStream ms = new MemoryStream(img);
                Image returnImage = Image.FromStream(ms);
                pictureBox1.Image = returnImage;
            }
        }
        private void setImage(byte[] img)
        {
            MemoryStream ms = new MemoryStream(img);
            Image returnImage = Image.FromStream(ms);
            pictureBox1.Image = returnImage;
        }

        private void textBox1_TextChanged(object sender, EventArgs e) { }


        private void button2_Click(object sender, EventArgs e)
        {
            setStateEnable(true);
            Thread stopthread = new Thread(new ThreadStart(stopServer));
            stopthread.Start();
        }

        private void stopServer()
        {
            try
            {
                foreach (IWebSocketConnection s in allSockets)
                {
                    s.Close();
                }
            }
            catch (Exception) { }
            allSockets.Clear();
            server.Dispose();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            textBox1.Clear();
        }

        private void write_toEditText(string text)
        {
            if (textBox1.InvokeRequired)
            {
                SetTextCallback d = new SetTextCallback(SetText);
                Invoke(d, new object[] { text });
            }
            else
            {
                textBox1.AppendText(text);
            }
        }
        private void SetText(string text)
        {
            textBox1.AppendText(text + "\n");
        }

        private void button4_Click(object sender, EventArgs e)
        {
            FolderBrowserDialog dialog = new FolderBrowserDialog();
            dialog.SelectedPath = Application.StartupPath;
            DialogResult result = dialog.ShowDialog();
            if (result == DialogResult.OK)
            {
                saveImagePath = dialog.SelectedPath;
            }
            textBox2.Text = saveImagePath;
        }

        private void button5_Click(object sender, EventArgs e)
        {
            OpenFileDialog dialog = new OpenFileDialog();
            dialog.Filter = "DLL|*.dll|Executable|*.exe";
            dialog.InitialDirectory = Application.StartupPath;
            if (dialog.ShowDialog() == DialogResult.OK)
            {
                algoPath = dialog.FileName;
            }
            textBox3.Text = algoPath;
        }

        private void button6_Click(object sender, EventArgs e)
        {

        }

        private void button7_Click(object sender, EventArgs e)
        {
            foreach (var socket in allSockets.ToList())
            {
                socket.Send("Broadcast: " + textBox5.Text + "\n");
            }
            write_toEditText("Broadcast: " + textBox5.Text + "\n");
        }

        private void pictureBox1_Click(object sender, EventArgs e)
        {

        }

        private void Form1_MouseMove(object sender, MouseEventArgs e)
        {
            if (textBox2 == this.GetChildAtPoint(e.Location))
            {
                if (!isShown)
                {
                    toolTip.Show(textBox2.Text, this, e.Location);
                    isShown = true;
                }
            }
            else if (textBox3 == this.GetChildAtPoint(e.Location))
            {
                if (!isShown)
                {
                    toolTip.Show(textBox3.Text, this, e.Location);
                    isShown = true;
                }
            }
            else
            {
                toolTip.Hide(textBox1);
                isShown = false;
            }
        }

        private ImageCodecInfo GetEncoder(ImageFormat format)
        {
            ImageCodecInfo[] codecs = ImageCodecInfo.GetImageDecoders();
            foreach (ImageCodecInfo codec in codecs)
                if (codec.FormatID == format.Guid)
                    return codec;
            return null;
        }

    }
}
