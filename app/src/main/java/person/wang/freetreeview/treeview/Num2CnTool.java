package person.wang.freetreeview.treeview;

import java.util.Arrays;
import java.util.List;

public class Num2CnTool {



    public static String getGrade(String position){
        String nums[]={"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19"};
        String cns[]={"零","一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五","十六","十七","十八","十九","二十"};

        List<String> stringA = Arrays.asList(nums);
        List<String> stringB = Arrays.asList(cns);

        for(int i=0;i<stringA.size();i++){

            if(stringA.get(i).equals(position)){

                return stringB.get(i+1);


            }

        }

        return "N";
    }


}
