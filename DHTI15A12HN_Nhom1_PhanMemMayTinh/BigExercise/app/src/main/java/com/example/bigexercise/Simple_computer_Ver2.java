package com.example.bigexercise;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

public class Simple_computer_Ver2 extends AppCompatActivity {

    private TextView tvExpression, tvResult;
    private StringBuilder expression = new StringBuilder();
    //Khai báo và khởi tạo một đối tượng StringBuilder để dễ dàng thao tác trong chuỗi biểu thức.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.simple_computer_ver2);

        tvExpression = findViewById(R.id.tvExpression);
        tvResult = findViewById(R.id.tvResult);

        Button[] numberButtons = new Button[]{
                findViewById(R.id.btn0),
                findViewById(R.id.btn1),
                findViewById(R.id.btn2),
                findViewById(R.id.btn3),
                findViewById(R.id.btn4),
                findViewById(R.id.btn5),
                findViewById(R.id.btn6),
                findViewById(R.id.btn7),
                findViewById(R.id.btn8),
                findViewById(R.id.btn9)
        };

        for (Button button : numberButtons) {
            button.setOnClickListener(v -> appendToExpression(((Button) v).getText().toString()));
        }


        //Thiết lập sự kiện cho các nút chức năng (cộng, trừ, nhân, chia, v.v.)
        // bằng cách liên kết với các phần tử giao diện có ID tương ứng và
        // gọi phương thức thích hợp khi nhấn (ghi tiếp vào text view khi nhấn nút đó qua hàm appendToExpression).
        findViewById(R.id.btnAdd).setOnClickListener(v -> appendToExpression("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(v -> appendToExpression("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> appendToExpression("*"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> appendToExpression("/"));
        findViewById(R.id.btnMod).setOnClickListener(v -> appendToExpression("%"));
        findViewById(R.id.btnOpenParenthesis).setOnClickListener(v -> appendToExpression("("));
        findViewById(R.id.btnCloseParenthesis).setOnClickListener(v -> appendToExpression(")"));
        findViewById(R.id.btnSin).setOnClickListener(v -> appendToExpression("sin("));
        findViewById(R.id.btnCos).setOnClickListener(v -> appendToExpression("cos("));
        findViewById(R.id.btnTan).setOnClickListener(v -> appendToExpression("tan("));
        findViewById(R.id.btnCot).setOnClickListener(v -> appendToExpression("cot("));
        findViewById(R.id.btnDecimal).setOnClickListener(v -> appendToExpression("."));

        findViewById(R.id.btnEqual).setOnClickListener(v -> calculateResult());
        findViewById(R.id.btnAC).setOnClickListener(v -> clearExpression());
        findViewById(R.id.btnDEL).setOnClickListener(v -> deleteLastCharacter());
        findViewById(R.id.btnPlusMinus).setOnClickListener(v -> togglePlusMinus());

        //Thiết lập sự kiện cho nút Menu(nhảy về giao diện chính)
        findViewById(R.id.btnMenu).setOnClickListener(v -> {
            Intent intent = new Intent(Simple_computer_Ver2.this, Menu_activity.class);
            startActivity(intent);
        });
    }

    //hàm cập nhật textView của biểu thức khi nhấp vào nút
    private void appendToExpression(String str) {
        expression.append(str);
        tvExpression.setText(expression.toString());
    }

    //hàm xóa biểu thức và kết quả
    private void clearExpression() {
        expression.setLength(0);
        tvExpression.setText("");
        tvResult.setText("");
    }

    //hàm xóa ký tự cuối cùng
    private void deleteLastCharacter() {
        if (expression.length() > 0) {
            expression.deleteCharAt(expression.length() - 1);
            tvExpression.setText(expression.toString());
        }
    }

    //hàm chuyển đổi dấu +/-
    private void togglePlusMinus() {
        if (expression.length() > 0) {
            int length = expression.length();
            // Kiểm tra dấu +/- ở cuối biểu thức
            int startIdx = length - 1;// chỗ đặt dấu +/-
            // Lấy vị trí dấu +/- cuối cùng thông qua vòng lập while
            //hiểu đơn giản miễn nó còn là số hoặc là dấu "." thì tiếp tục lùi lại đến cuối
            while (startIdx >= 0 && (Character.isDigit(expression.charAt(startIdx)) || expression.charAt(startIdx) == '.')) {
                startIdx--;
            }

            //nếu vị trí đầu không có gì hoặc dấu có dấu () thi thêm dấu - vào trước
            if (startIdx == -1 || expression.charAt(startIdx) == '(' || expression.charAt(startIdx) == ')') {
                // Chỉ có số nguyên hoặc số thực
                //nếu có dấu "-" rồi thì cho bay hơi luôn
                if (expression.charAt(0) == '-') {
                    expression.deleteCharAt(0);
                } else {
                    expression.insert(0, '-');
                }
            } else {
                // Trường hợp có toán tử ở trước thay thế nó bằng dấu +/-

                if (expression.charAt(startIdx) == '-') { // âm nhân âm bằng dương ó :>
                    expression.deleteCharAt(startIdx);
                    expression.insert(startIdx, '+');
                } else if (expression.charAt(startIdx) == '+') { //dương nhân âm bằng dương
                    expression.deleteCharAt(startIdx);
                    expression.insert(startIdx, '-');
                } else {
                    expression.insert(startIdx + 1, '-');
                }
            }
            tvExpression.setText(expression.toString());
        }
    }

    //hàm tính toán kết quả
    private void calculateResult() {
        try {
            String infix = expression.toString(); //Lấy biểu thức infix từ StringBuilder expression(toan tu trung to).
            String postfix = infixToPostfix(infix); //Chuyển đổi biểu thức sang postfix.
            double result = evaluatePostfix(postfix); //Tính toán kết quả từ biểu thức postfix.
            tvResult.setText(String.valueOf(result));//Hiển thị kết quả tính toán lên TextView.
        } catch (Exception e) {
            tvResult.setText("Lỗi rồi TwT");
        }
    }

    //hàm chuyển đổi biểu thức từ trung tố(infix) sang hậu tố(postfix)
    private String infixToPostfix(String expression) {
        StringBuilder result = new StringBuilder(); //Tạo đối tượng StringBuilder để xây dựng biểu thức postfix.
        Stack<String> stack = new Stack<>();//Tạo đối tượng Stack để lưu các toán tử và dấu ngoặc.

        for (int i = 0; i < expression.length(); i++) { //Duyệt qua từng ký tự của 'biểu thức' infix(Trung tố).
            char c = expression.charAt(i); //Lấy ký tự tại vị trí i ở trong biểu thức infix.

            if (Character.isDigit(c) || c == '.') { //Nếu ký tự là chữ số hoặc dấu chấm, thêm vào chuỗi kết quả.
                result.append(c);

            } else if (c == '(') { //Nếu ký tự là dấu ngoặc mở, thêm vào stack.
                stack.push(c + "");
                result.append(' '); // Đảm bảo khoảng trắng giữa các toán hạng

            } else if (c == ')') {//Nếu ký tự là dấu ngoặc đóng, lấy ra các toán tử từ stack cho đến khi gặp dấu ngoặc mở.
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    result.append(' ').append(stack.pop());//Thêm toán tử từ stack vào chuỗi kết quả.
                }
                stack.pop();
            } else if (Character.isLetter(c)) { //nếu ký tự là chữ cái thêm vào stack
                StringBuilder func = new StringBuilder(); //tạo ra 1 chuỗi để lưu hàm lượng giác
                //vòng while này sẽ xét độ dài của biểu thức nếu dài hơn số vòng lập và ở đó có phải là chữ cái không và
                //Nếu ký tự là chữ cái thì thêm vào chuỗi func và tăng số vòng lập để lấy được hết 1 từ kiểu như sin cos tan cot
                while (i < expression.length() && Character.isLetter(expression.charAt(i))) {
                    func.append(expression.charAt(i));
                    i++;
                }
                i--; // Lùi lại 1 bước vì vòng lặp for sẽ tăng lên 1 lần nữa
                stack.push(func.toString()); //thêm vào stack
                result.append(' ');
            } else {
                result.append(' ');

                //hiểu đơn giản cái vòng while này sẽ dùng hàm precedence để kiểm tra độ ưu tiên của toán tử
                //Nếu ký tự là toán tử, lấy ra các toán tử từ stack cho đến khi gặp toán tử có độ ưu tiên thấp hơn hoặc bằng( dùng hàm precedence).
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek().charAt(0))) {
                    result.append(stack.pop()).append(' ');
                }
                stack.push(c + "");
            }
        }

        while (!stack.isEmpty()) {
            result.append(' ').append(stack.pop());
        }

        //Lấy ra các toán tử từ stack và thêm vào kết quả.
        return result.toString();
    }

    //hàm kiểm tra độ ưu tiên của toán tử
    private int precedence(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            default:
                return -1;
        }
    }

    //hàm tính toán biểu thức postfix(hậu tố)
    private double evaluatePostfix(String postfix) throws Exception {
        Stack<Double> stack = new Stack<>();  //Tạo đối tượng Stack để lưu các toán hạng.
        String[] tokens = postfix.split(" "); //Tách biểu thức postfix thành các token.

        for (String token : tokens) { //Duyệt qua từng token trong biểu thức postfix.
            if (token.isEmpty()) {
                continue;
            }

            if (isNumber(token)) { //(dùng hàm isNumber)Nếu token là số, thêm vào stack.
                stack.push(Double.parseDouble(token));

            } else if (isTrigFunction(token)) { //(dùng hàm isTrigFunction)Nếu token là hàm lượng giác, lấy toán hạng từ stack, tính toán và đẩy kết quả vào stack.
                double b = stack.pop();
                switch (token) {
                    case "sin":
                        stack.push(Math.sin(Math.toRadians(b)));
                        break;
                    case "cos":
                        stack.push(Math.cos(Math.toRadians(b)));
                        break;
                    case "tan":
                        stack.push(Math.tan(Math.toRadians(b)));
                        break;
                    case "cot":
                        stack.push(1 / Math.tan(Math.toRadians(b)));
                        break;
                    default:
                        throw new Exception("Toán Tử không hợp lệ ròi 0w0");

                }
            } else { //Nếu token là toán tử, lấy hai toán hạng từ stack, tính toán và đẩy kết quả vào stack.
                double b = stack.pop();
                double a = stack.isEmpty() ? 0 : stack.pop();

                switch (token) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        stack.push(a / b);
                        break;
                    case "%":
                        stack.push(a % b);
                        break;
                    default:
                        throw new Exception("Toán Tử không hợp lệ ròi 0w0");
                }
            }
        }
        return stack.pop(); //Trả về kết quả cuối cùng từ stack.
    }

    //hàm kiểm tra token có phải là số hay không
    private boolean isNumber(String token) {

        //chuyển đổi chuỗi thành double. Nếu thành công, trả về true.không thì false.
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //hàm kiểm tra token có phải là hàm lượng giác hay không
    private boolean isTrigFunction(String token) {

        //Nếu chuỗi là một trong các hàm lượng giác, trả về true.
        return token.equals("sin") || token.equals("cos") || token.equals("tan") || token.equals("cot");
    }
}
