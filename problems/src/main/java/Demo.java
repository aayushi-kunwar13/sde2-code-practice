import dsa.BullAndCows;
import dsa.PaymentPArser;
import dsa.Practice1;
import dsa.RankTeamByVotes;

import java.util.List;

public class Demo {
//    public static void main(String[] args) {
//        Practice1 p = new Practice1();
//
//    }

    public static void main(String[] args) {
//        String secret = "1807";
//        String guess = "7810";
//        String result = BullAndCows.getHint(secret , guess);
//        System.out.println(result);
//        String[] votes = {"ABC","ACB","ABC","ACB","ACB"};
//        String ans = RankTeamByVotes.rankTeams(votes);
//        System.out.println(ans);

        String payment =  "payment5,1000,Paying off: invoiceC";
        String[] invoices = {
                "invoiceA,2024-01-01,100",
                "invoiceB,2024-02-01,200",
                "invoiceC,2023-01-30,1000"
        };

        String result = PaymentPArser.paymentDetail(payment, invoices);
        System.out.println(result);


    }

}
