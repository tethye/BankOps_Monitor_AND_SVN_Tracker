package com.example.myproject;

import com.example.myproject.Service.ClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.*;

@SpringBootApplication
public class MyProjectApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MyProjectApplication.class, args);

//        ClientService cs = context.getBean(ClientService.class);
//        Map<String, String> in = new HashMap<>();
//        in.put("NAME_XML", "tithi");
//        in.put("END_SL", "10");
//        in.put("START_SL", "1");
//        in.put("REPORT_SL", "1");
//        System.out.println(cs.getClientsData(in));




        List<Integer> nums = new ArrayList<>(Arrays.asList(50, 20, 20, 10, 10, 10, 10, 1));
         // int[] nums = {15, 14, 15, 17, 21, 32, 37, 5};
        //  int[] nums1 = {20, 50, 100, 200, 100, 50, 20, 60};

        int maxSum = maxSumDiv30(nums);
          System.out.println( "maximum summation is : " + maxSum);


        nums.sort(Collections.reverseOrder());

        for (int n : nums) {
            System.out.print(n + "   ");
        }
        System.out.println();

        List<Integer> ans = new ArrayList<>();

        findIndx(0, maxSum, nums, ans);

        System.out.println("ans size : " + ans.size());
        for (int idx : ans) {
            System.out.print(idx + "   ");
        }
        System.out.println();

    }

    ///  check book problem 1 solution (only find the multiplication)
    public static int maxSumDiv30(List<Integer> nums){
        int[] dp = new int[30];
        for(int num : nums){
            int tmp[] = Arrays.copyOf(dp,30);
            for(int i=0;i<30;i++){
                int rem = (num+tmp[i])%30;
                dp[rem] = Math.max(dp[(num+tmp[i])%30],num+tmp[i]);
            }
//            for(int n : dp){
//                System.out.print(n+" ");
//            }
//            System.out.println();
        }
        return dp[0];
    }

    public static boolean findIndx(int ind, int target, List<Integer> nums, List<Integer> ans) {
        if (target == 0) {
            return true;
        }

        for (int i = ind; i < nums.size(); i++) {
            if (nums.get(i) > target) continue;

            ans.add(i);
            if (findIndx(i + 1, target - nums.get(i), nums, ans)) {
                return true;
            }
            ans.remove(ans.size() - 1);
        }

        return false;
    }



}
