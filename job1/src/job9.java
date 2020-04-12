/**
 * 计算Hello World! 中出现了几次l。
 */
public class job9 {
    public static void main(String[] ages){
        String s = "Hello World!";
        int sum = 0;
        for(int i = 0; i < s.length(); i++){
            char a = s.charAt(i);
            if(a == 'l'){
                sum++;
            }
        }
        System.out.println(sum);
    }
}
