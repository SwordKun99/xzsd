/**
 * 打印101-200只有的所有素数，并求出所素数的和的代码
 */
public class job3 {
    public static void main(String[] ages){
        int sum = 0;
        for(int i = 101; i <= 200; i++) {
            boolean flag = true;
            for(int j = 2; j < i; j++){
                if(i % j == 0){
                    flag = false;
                    break;
                }
            }
            if(flag == true){
                sum = sum + i;
                System.out.println(i);
            }
        }
        System.out.print(sum);
    }
}
