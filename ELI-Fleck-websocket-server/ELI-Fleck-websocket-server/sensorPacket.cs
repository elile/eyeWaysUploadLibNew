﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ELI_Fleck_websocket_server
{
    class sensorPacket
    {
        public string ns { get; set; }
        public string type { get; set; }
        public string targetId { get; set; }
        public string sourceId { get; set; }
        public string sender { get; set; }
        public string data { get; set; }
        public string utid { get; set; }
    }
}
