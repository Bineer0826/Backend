package array;

public class NonrectangleArray {
    public static void main(String[] args) {

        //비정방배열
        //방법 1 선언 + 값 대입
        int[][] array1 = new int[2][3];
        // 이렇게 되면 2행 3열이 되므로 열이 고르지 않는 비정방행렬에서 나올 수 없다

        int[][] array2 = new int[2][]; //그래서 몇 행인지만 나타내고 array는 참조변수인  int배열을 잘 지정해준 것
        array2[0] = new int[2]; // 0번째 행에는 2열을 지정한다
        array2[0][0] = 1; //배열의 원소들을 다 지정해준다
        array2[0][1] = 2;

        array2[1] = new int[3]; // 1번쨰 행의 3열을 서
        array2[1][0] = 3;
        array2[1][1] = 4;
        array2[1][2] = 5;


        int[][] array7 = new int[2][];//동일하게 행 선언
        array7[0] = new int []{1,2}; //세부 값을 적어준다
        array7[1] = new int[]{3,4,5};


        //방법2 직접적으로 원소 나타내기
        int[][]array3 = new int[][] {{1,2}, {3,4,5}};
        int[][] array4;
        array4 = new int[][]{{1,2}, {3,4,5}};//분리작성


        //방법3
        int[][] array5 = {{1,2}, {3,4,5}};// new 뺴고
//        int[][] array6;
//        array6 = {{1,2}, {,3,4,5}};
//        분리하는건 오류가 난다
    }
}
