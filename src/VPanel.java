/**
 * 보여지는 컴포넌트 설정 및 관리은 모두 VPanel에서 담당
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VPanel extends JPanel {
    static int clickNum = 0;

    /** 입력값(원데이터, 다항코드)관련 컴포넌트 **/
    private JLabel dInputLabel;
    private JLabel pInputLabel;
    private JTextField originData;
    private JTextField polynomialCode;
    private JButton inputBtn = new JButton("Get Checksum");

    /** 결과값 관련 컴포넌트 **/
    private JLabel remainderLabel;
    private JTextField remainderField;

    private JLabel resultLabel;
    private JTextField resultField;

    private JLabel errorLabel;
    private JTextField errorField;

    /** 연산 과정 관련 컴포넌트 **/
    private JLabel calcuLabel;
    private ArrayList<JTextField> calcuFieldList;

    /** 연산 로직 실행을 위한 요소 (컴포넌트 X) **/
    private CheckSum checkSum;

    public VPanel() {
        // 자기자신 속성 설정
        this.setLayout(null);
        this.setOpaque(false);

        this.setComponents();   // 컴포넌트 속성 설정
        this.checkSum = new CheckSum();
    }

    private void setComponents() {
        // 이하 컴포넌트 속성 설정
        this.setCompOfInput();
        this.setCompOfResults();
        this.setCalculationArea();
    }


    private void setCompOfInput() {
        /** 데이터 입력과 관련된 컴포넌트 속성 설정 메소드 **/

        // originData(원 데이터) 입력과 관련된 컴포넌트
        dInputLabel = new JLabel("2진수 원 데이터 입력");
        this.add(dInputLabel);
        dInputLabel.setBounds(80,50,150,30);

        originData = new JTextField();
        originData.setBackground(Color.LIGHT_GRAY);
        this.add(originData);
        originData.setBounds(210,50,200,30);


        // polynomialCode(다항코드) 입력과 관련된 컴포넌트
        pInputLabel = new JLabel("2진수 다항코드 입력");
        this.add(pInputLabel);
        pInputLabel.setBounds(80,100,150,30);

        polynomialCode = new JTextField();
        polynomialCode.setBackground(Color.LIGHT_GRAY);
        this.add(polynomialCode);
        polynomialCode.setBounds(210,100,200,30);

        // input button
        this.add(inputBtn);
        inputBtn.setBounds(150,150,200,50);
    }

    private void setCompOfResults() {
        /** checksum 연산 결과와 관련된 컴포넌트 속성 설정 메소드 **/

        // checksum 나머지 값
        remainderLabel = new JLabel("CheckSum 결과의 나머지 값");
        this.add(remainderLabel);
        remainderLabel.setBounds(50,250,200,30);

        remainderField = new JTextField("Result will be here");
        remainderField.setBackground(Color.pink);
        remainderField.setForeground(Color.BLACK);
        remainderField.setEditable(false);
        this.add(remainderField);
        remainderField.setBounds(220,250,200,30);

        //  checksum 결과 (전송 데이터)
        resultLabel = new JLabel("결과 (원데이터+나머지)");
        this.add(resultLabel);
        resultLabel.setBounds(50,300,200,30);

        resultField = new JTextField("Result will be here");
        resultField.setBackground(Color.ORANGE);
        resultField.setForeground(Color.BLACK);
        resultField.setEditable(false);
        this.add(resultField);
        resultField.setBounds(220,300,200,30);


        // 전송 오류 유무 결과
        errorLabel = new JLabel("전송 오류 유무");
        this.add(errorLabel);
        errorLabel.setBounds(50,350,200,30);

        errorField = new JTextField("Result will be here");
        errorField.setForeground(Color.BLACK);
        errorField.setEditable(false);
        this.add(errorField);
        errorField.setBounds(220,350,200,30);
    }

    private void setCalculationArea() {
        /** checksum 연산 결과 과정을 보여주는 컴포넌트 속성 설정 메소드 **/

        calcuLabel = new JLabel("Checksum 연산 과정 (모듈러 연산)");
        this.add(calcuLabel);
        calcuLabel.setBounds(150,450,200,30);

        calcuFieldList = new ArrayList<>();
        calcuFieldList.add(new JTextField("( Checksum 연산 과정의 피제수,제수의 값들이 여기에 표시됩니다. )"));
        calcuFieldList.get(0).setEditable(false);
        this.add(calcuFieldList.get(0));
        calcuFieldList.get(0).setBounds(50,500,400,30);

    }

    public void setActionListener(ActionListener parent) {
        /** 이벤트가 발생할 컴포넌트에 대해, 각각 ActionListener를 연결시켜주는 메소드 **/
        this.inputBtn.addActionListener(parent);
    }

    public void actionPerformed(ActionEvent e) {
        /** "Get Checksum" 버튼이 눌렸을 때의 이벤트 처리 메소드 **/
        if (e.getSource() == this.inputBtn) {
            /** 이벤트가 발생한 컴포넌트가 "Get Checksum" 버튼이라면 아래의 로직 실행 **/

            ++clickNum;
            if(clickNum > 1){
                // 동일한 입력값에 대해 "Get Checksum" 버튼을 두 번 이상 클릭했을 경우에 대한 예외처리
                JOptionPane.showMessageDialog(null, "체크섬 연산이 이미 수행되었습니다.");
                return;
            }

            ArrayList<String> result = checkSum.getCheckSum(originData.getText(), polynomialCode.getText());    // 체크섬 연산을 실행한 후, 나머지 결과와 전송 데이터(원 데이터 + 나머지)를 반환받아 저장
            String remainger = result.get(0);   // 나머지 값
            String sendingData = result.get(1); // 전송 데이터 (원 데이터 + 체크섬 연산의 마지막 나머지)

            /** 연산의 최종 나머지 값과 전송 데이터 값 표시 **/
            remainderField.setText(remainger);  // 최종 나머지 값 표시
            resultField.setText(sendingData);   // 전송 데이터 (원 데이터 + 체크섬 연산의 나머지) 표시

            /** 전송 오류의 유무 값 표시 **/
            if(checkSum.isError(remainger))errorField.setText("YES");   // 체크섬 연산의 최종 나머지 값이 0이면 전송 오류 X
            else errorField.setText("NO");  // 체크섬 연산의 최종 나머지 값이 0이 아니면 전송 오류 O

            /** 체크섬의 연산 과정에서 나타나는 모든 모듈러 연산 과정을 표시 **/
            ArrayList<ArrayList<String>> tempResultList = checkSum.getTempResultList();
            String calcuText = "";
            for (int i = 0; i < tempResultList.size(); i++) {
                calcuText = (i+1) + "번째 연산 --> " + tempResultList.get(i).get(0)
                                + " ^ " + tempResultList.get(i).get(1)  // tempResultList(2차원 배열)의 각 행(1차원 배열)은 연산에 사용되는 값 두 개와 모듈러 연산 결과값, 총 세 개의 문자열로 구성
                                + " = " + tempResultList.get(i).get(2); // tempResultList.get(i).get(2) == i번째 모듈러 연산의 결과 값
                if(i == 0){ calcuFieldList.get(i).setText(calcuText); }
                else {
                    // 각 모듈러 연산 과정 값 표시
                    calcuFieldList.add(new JTextField(calcuText));
                    calcuFieldList.get(i).setEditable(false);
                    this.add(calcuFieldList.get(i));
                    calcuFieldList.get(i).setBounds(50, 500+i*30, 400, 30);
                }
            }
        }
    }
}
