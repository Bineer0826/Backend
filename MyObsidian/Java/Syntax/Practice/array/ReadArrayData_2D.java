package JavaSyntax.array;

public class ReadArrayData_2D {
    public static void main(String[] args) {

        //2차원 데이터의 배열의 길이

        //정방행렬
        int[][] array1 = new int[2][3];
        System.out.println(array1.length);//행에 대한 길이가 나온다 : 2
        System.out.println(array1[0].length); //열에 대한 길이가 나온다 ; 3
        //데이터값들일 일직선으로 연결되기 떄문이다
        System.out.println(array1[1].length); //3

        System.out.println();

        //비정방행렬
        int[][] array2;
        array2 = new int[][]{{1,2},{3,4,5}};

        System.out.println(array2.length); //행에 대한 길이이므로 2
        System.out.println(array2[1].length);  //3
        System.out.println(array2[0].length); //2


        //2차원 배열의 출력방법
        System.out.println(array2[0][0]);
        System.out.println(array2[0][1]);
        System.out.println(array2[1][0]);
        System.out.println(array2[1][1]);
        System.out.println(array2[1][2]);
        System.out.println();
        //for문으로 출력

        for(int i=0; i< array2.length; i++){ //행 먼저 2번 반복
            for( int j=0; j< array2[i].length; j++){ //행이 각 차례마다 열을 나타낸다 array2[0].lengt보다 작을때가지 시행
                System.out.println(array2[i][j]);
            }

        }

        //for-each문 이용
        for(int[] array: array2) {
            for(int k:array){
                System.out.println(k);
            }
        }

    }
}
