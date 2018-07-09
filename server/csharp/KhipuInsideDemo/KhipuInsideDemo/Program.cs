using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using Khipu;
using Khipu.Api;
using Khipu.Client;
using Khipu.Model;

namespace KhipuInsideDemo
{
    class Program
    {
        static void Main(String[] args)
        {
            String action = "";

            if(args.Length > 0)
            {
                action = args[0];
            }

            if (action.Equals("create"))
            {
                CreatePayment();
            } else
            {
                ValidatePayment();
            }
            
        }

        static void CreatePayment()
        {
            Configuration.Secret = File.ReadAllText("../../../../../SECRET").Trim();
            Configuration.ReceiverId = Int32.Parse(File.ReadAllText("../../../../../RECEIVER_ID").Trim());
            PaymentsApi a = new PaymentsApi();

            DateTime d = DateTime.Now;
            try
            {
                PaymentsCreateResponse r = a.PaymentsPost("Pago de Demo", "CLP", 100, notifyUrl: "https://micomercio.com/notify", transactionId: "TX-1234");

                System.Console.WriteLine("PAYMENT_ID: " + r.PaymentId);
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
            System.Console.Read();
        }
        static void ValidatePayment()
        {
            Configuration.Secret = File.ReadAllText("../../../../../SECRET").Trim();
            Configuration.ReceiverId = Int32.Parse(File.ReadAllText("../../../../../RECEIVER_ID").Trim());
            String notificationToken = File.ReadAllText("../../../../../NOTIFICATION_TOKEN").Trim();

            PaymentsApi a = new PaymentsApi();

            try
            {
                PaymentsResponse r = a.PaymentsGet(notificationToken);

                System.Console.WriteLine("PAYMENT_ID: " + r.PaymentId);
                System.Console.WriteLine("TRANSACTION_ID: " + r.TransactionId);
                System.Console.WriteLine("AMOUNT: " + r.Amount);
                System.Console.WriteLine("CURRENCY: " + r.Currency);
                System.Console.WriteLine("STATUS: " + r.Status);

            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
            System.Console.Read();
        }
    }
}
