import com.khipu.ApiClient;
import com.khipu.ApiException;
import com.khipu.api.client.PaymentsApi;
import com.khipu.api.model.PaymentsCreateResponse;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Demo {
    public static void main(String[] args) throws ApiException, IOException {
        Long receiverId = Long.parseLong(readSingleLineFile("../../RECEIVER_ID", Charset.defaultCharset()).trim()); //ID de cobrador
        String secret = readSingleLineFile("../../SECRET", Charset.defaultCharset()).trim(); //llave secreta

        ApiClient apiClient = new ApiClient();
        apiClient.setKhipuCredentials(receiverId, secret);
        PaymentsApi paymentsApi = new PaymentsApi();
        paymentsApi.setApiClient(apiClient);

        Map<String, Object> options = new HashMap<>();

        PaymentsCreateResponse response = paymentsApi.paymentsPost("Pago de demo" //Motivo de la compra
                , "CLP" //Moneda
                , 100.0 //Monto
                , options
        );

        System.out.println("PAYMENT_ID: " + response.getPaymentId());
    }

    static String readSingleLineFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding).trim();
    }
}
