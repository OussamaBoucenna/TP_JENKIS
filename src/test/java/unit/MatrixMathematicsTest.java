package unit;

import com.example.exception.NoSquareException;
import com.example.model.Matrix;
import com.example.service.MatrixMathematics;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class MatrixMathematicsTest {

    @Test
    public void testDeterminantNormalMatrix() throws NoSquareException {

        final Matrix testMatrix = new Matrix(new double[][]{
                {1,2,3},
                {4,5,6},
                {7,8,9}
        });

        assertEquals(0, MatrixMathematics.determinant(testMatrix), 0.0001);

    }

    @Test
    public void testDeterminantNonSquareMatrix() throws NoSquareException {

        final Matrix testMatrix = new Matrix(new double[][]{
                {1,2,3},
                {4,5,6}
        });

        assertThrows(
                NoSquareException.class,
                () -> MatrixMathematics.determinant(testMatrix));

    }

    @Test
    public void testDeterminantOneElementMatrix() throws NoSquareException {

        final Matrix testMatrix = new Matrix(new double[][]{
                {70}
        });

        assertEquals(70, MatrixMathematics.determinant(testMatrix), 0.0001);

    }
}