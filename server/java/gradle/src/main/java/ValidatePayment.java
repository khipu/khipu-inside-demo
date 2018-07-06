import com.khipu.ApiClient;
import com.khipu.ApiException;
import com.khipu.api.client.PaymentsApi;
import com.khipu.api.model.PaymentsCreateResponse;
import com.khipu.api.model.PaymentsResponse;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ValidatePayment {
    public static void main(String[] args) throws ApiException, IOException {
        Long receiverId = Long.parseLong(readSingleLineFile("../../RECEIVER_ID").trim()); //ID de cobrador
        String secret = readSingleLineFile("../../SECRET").trim(); //llave secreta

        String notificationToken = readSingleLineFile("../../NOTIFICATION_TOKEN").trim(); //token de un pago

        ApiClient apiClient = new ApiClient();
        apiClient.setKhipuCredentials(receiverId, secret);
        PaymentsApi paymentsApi = new PaymentsApi();
        paymentsApi.setApiClient(apiClient);

        PaymentsResponse response = paymentsApi.paymentsGet(notificationToken);

        System.out.println("PAYMENT_ID: " + response.getPaymentId());
        System.out.println("TRANSACTION_ID: " + response.getTransactionId());
        System.out.println("AMOUNT: " + response.getAmount());
        System.out.println("CURRENCY: " + response.getCurrency());
        System.out.println("STATUS: " + response.getStatus());
    }

    static String readSingleLineFile(String path)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, Charset.defaultCharset()).trim();
    }
}
