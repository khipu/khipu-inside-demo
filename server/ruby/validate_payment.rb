class ValidatePayment
  require 'khipu-api-client'

  Khipu.configure do |c|
    c.secret = File.read("../SECRET").strip
    c.receiver_id = File.read("../RECEIVER_ID").strip
    c.platform = 'demo-client'
    c.platform_version = '2.0'
    #c.debugging = true
  end

  client = Khipu::PaymentsApi.new

  response = client.payments_get(File.read("../NOTIFICATION_TOKEN").strip)

  print "PAYMENT_ID: " + response.payment_id + "\n"
  print "AMOUNT: " +  response.amount.to_s + "\n"
  print "CURRENCY: " + response.currency + "\n"
  print "STATUS: " + response.status + "\n"

end

