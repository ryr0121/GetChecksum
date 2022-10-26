import java.util.ArrayList;

/** [ 체크섬 구하기 과정 ]
 *
 * 1. 원데이터(m)와 생성 다항식(g)을 입력 받음
 * 2. 원데이터의 뒤에 나머지 보관을 위한 생성 및 0으로 초기화 된 추가 공간을 붙임
 * 3. '원데이터/생성 다항식'의 연산을 진행 후, 구해진 나머지 값(이게 체크섬인가,,,,,)을 원데이터 뒤에 준비된 추가공간에 대입
 * 4. 나머지 값이 0인지 아닌지를 통해 오류 발생 여부를 확인 및 수신 호스트에 변경된 원데이터를 전송
 *
 **/

public class CheckSum {

    private ArrayList<ArrayList<String>> tempResultList;

    public CheckSum() { this.tempResultList = new ArrayList<>(); }

    public ArrayList<String> getCheckSum(String originData, String polynomialCode){
        // 원데이터의 끝부터 0으로 초기화된 추가공간 붙이기
        for (int i = 0; i < polynomialCode.length()-1; i++) { originData += '0'; }

        // 원 데이터의 첫 자리부터 다항코드 길이만큼만 추출하여 g에 저장
        String m = originData.substring(0,polynomialCode.length());
        String g = polynomialCode;  // 다항코드 복사
        String tempResult = ""; // (모듈러 연산을 위해) 나머지 값을 임시저장할 변수
        ArrayList<String> tempList; // 모듈러 연산할 피연산자 두 개, 연산 결과를 담을 임시 리스트

        // (모듈로 연산 횟수) = (원 데이터 길이) - (다항코드 길이)
        for (int i = polynomialCode.length(); i < originData.length() + 1; i++) {
            tempResult = "";    // 연산 과정 중에 발생하는 나머지를 임시 저장하기 위한 변수

            for (int j = 0; j < polynomialCode.length(); j++) {
                if(m.charAt(j) == g.charAt(j)) { tempResult += '0'; }
                else { tempResult += '1'; }
            }

            if(i < originData.length()){
                // tempList에는 모듈러 연산에 사용되는 m와 g, 그리고 모듈러 연산 결과를 저장함
                // 각 모듈러 연산에 대한 값을 tempList에 채우고, 그 tempList를 tempResultList에 저장함
                tempList = new ArrayList<>();
                tempList.add(m); tempList.add(g); tempList.add(tempResult); // 피연산자(m,g)와 연산 결과(tempResult)를 리스트에 추가
                tempResultList.add(tempList);

                // 다음 모듈러 연산을 위해 m,p값을 재정의
                // m는 이전 모듈러 연산 결과에서 (2번째 자리수부터 끝까지의 값) + (원 데이터에서 해당 연산에 대한 끝자리(연산 순서마다 달라지는 값임))으로 재정의
                // g는 m의 시작값이 0이면 0으로 채워진 값, 1이면 입력받은 다항코드의 값으로 재정의
                m = tempResult.substring(1) + originData.charAt(i);
                if(m.charAt(0) == '0'){
                    g = "";
                    for(int k=0; k<polynomialCode.length(); k++){ g += "0"; }
                }
                else { g = polynomialCode; }
            }

        }
        // 마지막 모듈러 연산에 대한 과정 및 결과값 저장
        tempList = new ArrayList<>();
        tempList.add(g); tempList.add(m); tempList.add(tempResult);
        tempResultList.add(tempList);

        ArrayList<String> result = new ArrayList<>();
        result.add(tempResult.substring(1));    // 최종 연산 결과인 나머지 값을 저장

        result.add(originData.substring(0, originData.length() - polynomialCode.length()+1)
                + result.get(0));

        // tempResultList -> 모듈러 연산 과정을 저장하는 2차원 배열
        // result -> 연산의 최종 결과(나머지)값과 전송 데이터(원 데이터 + 나머지)를 저장하는 1차원 배열

        return result;
    }

    public ArrayList<ArrayList<String>> getTempResultList() { return this.tempResultList; }


    // 체크섬 구한 후, 전송 오류가 있는지 확인하는 메소드 (체크섬 결과가 0이면 전송 오류 X, 0이 아니면 전송 오류 O)
    public boolean isError(String checksum){
        int result = Integer.parseInt(checksum, 2); // 문자열로 정의된 checksum 변수의 값에 대해, 2진수 -> 10진수로 변환
        if(result == 0) return false;
        else return true;
    }
}
