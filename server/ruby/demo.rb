class Demo
  require 'khipu-api-client'

  Khipu.configure do |c|
    c.secret = File.read("../SECRET").strip
    c.receiver_id = File.read("../RECEIVER_ID").strip
    c.platform = 'demo-client'
    c.platform_version = '2.0'
    #c.debugging = true
  end

  client = Khipu::PaymentsApi.new

  response = client.payments_post('Pago de demo', 'CLP', 100, {
  })

  print "PAYMENT_ID: " + response.payment_id + "\n"
end

