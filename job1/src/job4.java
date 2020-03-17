/**
 * 数组排序：{1,23,6,74,8,19,104} 按 从小到大排序
 */
import java.util.Arrays;

public class job4 {
    public static void main(String[] ages){
        int arr[] = {1,23,6,74,8,19,104};
        Arrays.sort(arr);
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
    }
}
