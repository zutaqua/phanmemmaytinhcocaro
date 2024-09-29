package com.example.bigexercise;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Caro_Game extends AppCompatActivity {
    private TextView tvTurn, tvResult, tvCountX, tvCountO;
    private GridLayout gridLayout;
    private Button btnReset;
    private boolean playerX = true; // true = Người chơi X, false = Người chơi O
    private int[][] board = new int[10][10]; // 0 = trống, 1 = X, 2 = O
    private List<Button> winningButtons = new ArrayList<>();

    // Khởi tạo giao diện
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caro_game);

        tvTurn = findViewById(R.id.tvTurn);
        tvResult = findViewById(R.id.tvResult);
        tvCountX = findViewById(R.id.tvCountX);
        tvCountO = findViewById(R.id.tvCountO);
        gridLayout = findViewById(R.id.gridLayout);
        btnReset = findViewById(R.id.btnReset);

        createBoard();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    // Tạo bảng chơi Tic Tac Toe
    private void createBoard() {
        int totalColumns = 10;
        int totalRows = 10;
        gridLayout.setColumnCount(totalColumns);
        gridLayout.setRowCount(totalRows);

        //tạo bẳng bằng vòng for lồng nhau và lấp đầy bằng button
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                //tạo 1 nút
                Button button = new Button(this);
                //thay đổi bố cục GridLayout
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;

                //xác định vị trí của 1 nút trong GridLayout, 1f là chiều rộng để cách nút bằng nhau
                params.columnSpec = GridLayout.spec(j, 1f);
                params.rowSpec = GridLayout.spec(i, 1f);
                params.setMargins(1, 1, 1, 1); // Đặt lề để các ô vuông không chạm vào nhau

                button.setLayoutParams(params);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleMove((Button) v);
                    }
                });
                gridLayout.addView(button);
            }
        }
    }

    // Xử lý sự kiện khi người chơi nhấp vào một ô vuông
    private void handleMove(Button button) {
        if (!button.getText().toString().isEmpty() || !tvResult.getText().toString().isEmpty()) {
            return; // Ô đã được chiếm hoặc đã có người thắng
        }

        // tạo biến int để xác định hàng và cột của nút được nhấn dựa trên vị trí của nó trong gridLayout.
        int row = gridLayout.indexOfChild(button) / 10;
        int col = gridLayout.indexOfChild(button) % 10;

        if (playerX) {
            button.setText("X");
            button.setTextColor(Color.RED); // Màu đỏ cho X
            board[row][col] = 1; //đánh dấu X là 1 ở vị trí hàng và cột được nhấn
        } else {
            button.setText("O");
            button.setTextColor(Color.BLUE); // Màu xanh cho O
            board[row][col] = 2; //đa dấu O là 2 ở vị trí hàng và cột được nhấn
        }

        if (checkWin()) {
            String winner = playerX ? "Người chơi X" : "Người chơi O";
            tvResult.setText(winner + " thắng!");
            tvResult.setVisibility(View.VISIBLE); //hiện textview kết quả
            btnReset.setVisibility(View.VISIBLE); //hiên button reset
            updateCounts(); // cập nhật số X và O có trên bảng
            highlightWinningCells(); // Tô màu nền các ô vuông chiến thắng
            gridLayout.setEnabled(false); // vô hiệu hóa các ô vuông
            return;
        }

        playerX = !playerX;
        tvTurn.setText(playerX ? "Lượt: Người chơi X" : "Lượt: Người chơi O");
        tvTurn.setTextColor(playerX ? Color.RED : Color.BLUE);
    }

    // Kiểm tra xem có người thắng hay không
    private boolean checkWin() {
        // Kiểm tra hàng, cột và đường chéo để xác định người thắng bằng hàm checkLine
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] != 0) {
                    if (checkLine(i, j, 1, 0) ||       // Kiểm tra hàng ngang
                            checkLine(i, j, 0, 1) ||   // Kiểm tra cột dọc
                            checkLine(i, j, 1, 1) ||   // Kiểm tra đường chéo \
                            checkLine(i, j, 1, -1)) {  // Kiểm tra đường chéo /
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Kiểm tra một đường thẳng để xác định người thắng
    private boolean checkLine(int row, int col, int dRow, int dCol) {
        int count = 0;
        int player = board[row][col];
        winningButtons.clear();
        for (int k = 0; k < 5; k++) {
            int r = row + k * dRow;
            int c = col + k * dCol;
            if (r < 0 || r >= 10 || c < 0 || c >= 10 || board[r][c] != player) {
                break;
            }
            winningButtons.add((Button) gridLayout.getChildAt(r * 10 + c));
            count++;
        }
        return count == 5;
    }

    // Tô màu nền các ô vuông chiến thắng
    private void highlightWinningCells() {
        for (Button button : winningButtons) {
            button.setBackgroundColor(Color.YELLOW); // Màu nền vàng cho ô vuông chiến thắng
        }
    }

    private void updateCounts() {
        int countX = 0, countO = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == 1) {
                    countX++;
                } else if (board[i][j] == 2) {
                    countO++;
                }
            }
        }
        tvCountX.setText("X: " + countX);
        tvCountO.setText("O: " + countO);
    }

    private void resetGame() {
        playerX = true;
        board = new int[10][10];
        tvTurn.setText("Lượt: Người chơi X");
        tvResult.setText("");
        tvResult.setVisibility(View.GONE);//ẩn textview kết quả
        btnReset.setVisibility(View.GONE);//ẩn button reset
        gridLayout.removeAllViews();//xóa tất cả các view trong GridLayout
        createBoard();//tạo lại bảng mới
        gridLayout.setEnabled(true);//cho phép thao tác các ô vuông trong GridLayout
    }
}
