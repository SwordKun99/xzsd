import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数组{1,2,3,4,5,5,5,5,5,6,7,8,9}，去掉数组中的5 生成新的数组。
 */
public class job5 {
    public static void main(String[] ages) {
        int str[] = {1,2,3,4,5,5,5,5,5,6,7,8,9};
        int a = 5;
        for(int i = 0; i < str.length; i++){
            if(str[i] != a){
                System.out.print(str[i] + " ");
            }
        }
    }
}
