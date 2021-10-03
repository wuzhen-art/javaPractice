/**
 * @author 吴振
 * @since 2021/10/4 上午2:36
 */
public class HttpPractice {
    public static void main(String[] args) {
        String url = "http://localhost:8801";
        try {
            String result = HttpClientHelper.getAsString(url);
            System.out.println("Get url :" + url + " ; response :" +  result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
