using ICSharpCode.SharpZipLib.Zip.Compression.Streams;
using System;
using System.Collections.Generic;
using System.IO;
using System.IO.Compression;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ELI_Fleck_websocket_server
{
    class Inflater
    {
        public static byte[] Decompress(byte[] Bytes)
        {
            InflaterInputStream stream = new InflaterInputStream(new MemoryStream(Bytes));
            MemoryStream memory = new MemoryStream();
            byte[] writeData = new byte[4096];
            int size;
            do
            {
                size = stream.Read(writeData, 0, writeData.Length);
                memory.Write(writeData, 0, size);
            } while (size > 0);
            stream.Close();
            return memory.ToArray();
        }
    }
}
