package com.example.bigexercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Simple_computer_Ver1 extends AppCompatActivity {
    private TextView tvExpression, tvResult;
    private String expression = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_computer_ver1);

        tvExpression = findViewById(R.id.tvExpression);
        tvResult = findViewById(R.id.tvResult);

        //sự kiện nhấp nút cho từng nút (duma làm cái cổn lù này tốn time vs hoa mắt vcl(chủ yếu do copy paste))
        Button btn0 = findViewById(R.id.btn0);
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("0");
            }
        });


        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("1");
            }
        });

        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("2");
            }
        });

        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("3");
            }
        });

        Button btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("4");
            }
        });

        Button btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("5");
            }
        });

        Button btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("6");
            }
        });

        Button btn7 = findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("7");
            }
        });

        Button btn8 = findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("8");
            }
        });

        Button btn9 = findViewById(R.id.btn9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("9");
            }
        });

        Button btnDecimal = findViewById(R.id.btnDecimal);
        btnDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression(".");
            }
        });

        Button btnPlusMinus = findViewById(R.id.btnPlusMinus);
        btnPlusMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlusMinus();
            }
        });

        Button btnSin = findViewById(R.id.btnSin);
        btnSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("sin(");
            }
        });

        Button btnCos = findViewById(R.id.btnCos);
        btnCos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("cos(");
            }
        });

        Button btnTan = findViewById(R.id.btnTan);
        btnTan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("tan(");
            }
        });

        Button btnCot = findViewById(R.id.btnCot);
        btnCot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("cot(");
            }
        });

        Button btnOpenParenthesis = findViewById(R.id.btnOpenParenthesis);
        btnOpenParenthesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("(");
            }
        });

        Button btnCloseParenthesis = findViewById(R.id.btnCloseParenthesis);
        btnCloseParenthesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression(")");
            }
        });

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("+");
            }
        });

        Button btnSubtract = findViewById(R.id.btnSubtract);
        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("-");
            }
        });

        Button btnMultiply = findViewById(R.id.btnMultiply);
        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("*");
            }
        });

        Button btnDivide = findViewById(R.id.btnDivide);
        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("/");
            }
        });

        Button btnMod = findViewById(R.id.btnMod);
        btnMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("mod");
            }
        });

        Button btnEqual = findViewById(R.id.btnEqual);
        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult();
            }
        });

        Button btnAC = findViewById(R.id.btnAC);
        btnAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearExpression();
            }
        });

        Button btnDEL = findViewById(R.id.btnDEL);
        btnDEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLastCharacter();
            }
        });
        Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Simple_computer_Ver1.this, Menu_activity.class);
                startActivity(intent);
            }
        });
    }

    // hàm thêm vào biểu thức (lưu vào biến expression(biểu thức) rồi hiện nó lên textView)
    private void appendToExpression(String str) {
        expression += str;
        tvExpression.setText(expression);
    }

    // hàm tính toán biểu thức
    // thực hiện phép tính rồi lưu vô biến result rồi hiện nó lên textView
    private void calculateResult() {
        try {
            double result = evaluateSimpleExpression(expression);
            tvResult.setText(String.valueOf(result));
        } catch (Exception e) {
            tvResult.setText("Lỗi ròi nhập lại đê");
        }
    }

    //hàm xóa toàn bộ biểu thức
    //cho biến expression(biểu thức) về rỗng , và cả các text view về rỗng luôn
    private void clearExpression() {
        expression = "";
        tvExpression.setText("");
        tvResult.setText("");
    }

    // hàm xóa 1 ký tự cuối cùng
    // độ dài kí tự trong expression -1 và hiện nó lên text view
    private void deleteLastCharacter() {
        if (expression.length() > 0) {
            expression = expression.substring(0, expression.length() - 1);
            tvExpression.setText(expression);
        }
    }
    private void togglePlusMinus() {
        if (expression.length() > 0) {
            if (expression.startsWith("-")) {
                expression = expression.substring(1);
            } else {
                expression = "-" + expression;
            }
            tvExpression.setText(expression);
        }
    }


    // hàm đánh giá biểu thức đơn giản "evaluateSimpleExpression" nó se danh giá biểu thức đơn giản như sau:
    // khi nhập vào 2 + 3 nó sẽ phát hiện ra dấu + và chia nhỏ toán tử ra 2 phần là "2" và "3"
    // sau khi đánh giá xong nó sẽ trả về kết quả là "2.0 và 3.0" và tính nó ra là "5.0"
    //nếu là phép 1+2*3 thì nó tách thành 2 phần là "1" và "2*3" rồi đánh giá từng phần riêng biệt
    // đánh giá phần 1 thành 1.0 rồi đánh giá phần 2 là 2*3 nó sẽ tách ra thành 2.0*3.0 và tính kq là 6.0
    //rồi quay lại cộng tiếp thành 1.0 + 6.0 = 7.0



    // hàm đánh giá biểu thức đơn giản
    private double evaluateSimpleExpression(String expression) throws Exception {
        // Thực hiện cơ bản việc đánh giá biểu thức
        // Xử lý các hoạt động +, -, *, /, sin, cos, tan, cot, mod
        // Lưu ý: dell thể thực hiện quá 3 phép tính thực ra cũng do yêu cầu đề cũng chỉ cần 1-2 đâm ra
        // hơi lười chút ae thông cảm :>

        if (expression.contains("+")) {  //nếu phát hiện ra phép tinh là cộng
            String[] parts = expression.split("\\+");
            return evaluateSimpleExpression(parts[0]) + evaluateSimpleExpression(parts[1]);

        } else if (expression.contains("-")) { //nếu phát hiện ra phép tinh là trừ
            String[] parts = expression.split("-");
            return evaluateSimpleExpression(parts[0]) - evaluateSimpleExpression(parts[1]);

        } else if (expression.contains("*")) { //nếu phát hiện ra phép tinh là nhân
            String[] parts = expression.split("\\*");
            return evaluateSimpleExpression(parts[0]) * evaluateSimpleExpression(parts[1]);

        } else if (expression.contains("/")) { //nếu phát hiện ra phép tinh là chia
            String[] parts = expression.split("/");
            return evaluateSimpleExpression(parts[0]) / evaluateSimpleExpression(parts[1]);

        } else if (expression.contains("mod")) { //nếu phát hiện ra phép tinh là chia lấy dư
            String[] parts = expression.split("mod");
            return evaluateSimpleExpression(parts[0]) % evaluateSimpleExpression(parts[1]);

        } else if (expression.startsWith("sin(")) { //nếu phát hiện ra phép tinh bắt đầu là sin
            String innerExpression = expression.substring(4, expression.length() - 1);
            return Math.sin(Math.toRadians(evaluateSimpleExpression(innerExpression)));

        } else if (expression.startsWith("cos(")) { //nếu phát hiện ra phép tinh bắt đầu là cos
            String innerExpression = expression.substring(4, expression.length() - 1);
            return Math.cos(Math.toRadians(evaluateSimpleExpression(innerExpression)));

        } else if (expression.startsWith("tan(")) { //nếu phát hiện ra phép tinh bắt đầu là tan
            String innerExpression = expression.substring(4, expression.length() - 1);
            return Math.tan(Math.toRadians(evaluateSimpleExpression(innerExpression)));

        } else if (expression.startsWith("cot(")) { //nếu phát hiện ra phép tinh bắt đầu là cot
            String innerExpression = expression.substring(4, expression.length() - 1);
            return 1 / Math.tan(Math.toRadians(evaluateSimpleExpression(innerExpression)));
        } else {
            return Double.parseDouble(expression);
        }
    }
}
