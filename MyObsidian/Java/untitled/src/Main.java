import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //n장 소지,, 도장 k개 모으면 쿠폰 1개 , 최대 몇마리나 먹을 수 있는지?
        //쿠폰 n장이 있으면 n개의 도장을 찍어주고 k개가 모이면 쿠폰 1장
        //n 4, k 3이라면 4장소지,도장 4개 찍어주고 3개마다 쿠폰 한장 치킨은 총5마리 도장 2개쌓인상태
        //10 3 이라면 10장 소지, 도장 10개 찍고 3개의 쿠폰 생기고 3개쿠폰 사용마다 도장 3개 1개 총 14마ㅣㄹ

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            int k = sc.nextInt();

            if (n == 0 && k == 0) {
                break;//무조건 치킨을 먹어야 하니까 둘다 0일 경우는 제외
            } else {

                int chicken = 0;//치킨 수의 초기값은 0
                chicken += n;//쿠폰 수만큼 치킨 수가 됨

                while (n >= k) { //도장 수 K가 쿠폰 수n보다 작을 경우

                    n = n / k; //3개 도장에 쿠폰 하나라면 10인 경우 3개의 쿠폰 적립
                    chicken += n;//치킨으로 반환
                }
                System.out.println(chicken);
            }

        }


    }
}