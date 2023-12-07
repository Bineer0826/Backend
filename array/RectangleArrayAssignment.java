package array;

public class RectangleArrayAssignment {
    public static void main(String[] args) {
        //방법1 배열의 원소값 대입

        int[][] array1 = new int[2][3];
        array1[0][0] = 1;//힙메모리에 0이 이미 있고 이렇게 지정하면 화
        array1[0][1] = 2;
        array1[0][2] = 3;
        array1[1][0] = 4;
        array1[1][1] = 5;
        array1[1][2] = 6;

        System.out.println(array1[0][0]+" "+ array1[0][1]+ " " + array1[0][2]);
        System.out.println(array1[1][0]+" "+ array1[1][1]+ " " + array1[1][2]);

        //방법2 선언 후 값대입

        int[][] array2;
        array2 = new int[2][3];//분리작성
        array2[0][0] = 1;//힙메모리에 0이 이미 있고 이렇게 지정하면 화
        array2[0][1] = 2;
        array2[0][2] = 3;
        array2[1][0] = 4;
        array2[1][1] = 5;
        array2[1][2] = 6;

        System.out.println(array2[0][0]+" "+ array2[0][1]+ " " + array2[0][2]);
        System.out.println(array2[1][0]+" "+ array2[1][1]+ " " + array2[1][2]);

    }

}
