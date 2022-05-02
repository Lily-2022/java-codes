package register.client;

class RegisterClientTest {

    public static void main(String[] args) throws Exception{
        RegisterClient registerClient = new RegisterClient();
        registerClient.start();

        Thread.sleep(5000);
        registerClient.shutdown();
    }

}