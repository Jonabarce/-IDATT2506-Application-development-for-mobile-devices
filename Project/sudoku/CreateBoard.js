function generateEmptyBoard() {
    const board = [];
    for (let i = 0; i < 9; i++) {
    board[i] = [];
    for (let j = 0; j < 9; j++) {
        board[i][j] = 0;
    }
    }
    return board;
}

function isValid(board, row, col, num) {
    for (let i = 0; i < 9; i++) {
    if (board[row][i] === num) {
        return false;
    }
    }

    for (let i = 0; i < 9; i++) {
    if (board[i][col] === num) {
        return false;
    }
    }

    const boxStartRow = Math.floor(row / 3) * 3;
    const boxStartCol = Math.floor(col / 3) * 3;
    for (let i = boxStartRow; i < boxStartRow + 3; i++) {
    for (let j = boxStartCol; j < boxStartCol + 3; j++) {
        if (board[i][j] === num) {
        return false;
        }
    }
    }

    return true;
}

function fillBoard(board) {
    for (let row = 0; row < 9; row++) {
    for (let col = 0; col < 9; col++) {
        if (board[row][col] === 0) {
        for (let num = 1; num <= 9; num++) {
            if (isValid(board, row, col, num)) {
            board[row][col] = num;
            if (fillBoard(board)) {
                return true;
            }
            board[row][col] = 0;
            }
        }
        return false;
        }
    }
    }
    return true;
}
    function generateSudokuBoard(difficulty) {
    const board = generateEmptyBoard();
    fillBoard(board);

    const removeCount = difficulty === 'lett' ? 30 : difficulty === 'middels' ? 40 : 50;

    for (let i = 0; i < removeCount; i++) {
    const row = Math.floor(Math.random() * 9);
    const col = Math.floor(Math.random() * 9);
    if (board[row][col] !== 0) {
        board[row][col] = 0;
    }
    }

    return board;
}

const sudokuBoard = generateSudokuBoard('lett');
console.log(sudokuBoard);
