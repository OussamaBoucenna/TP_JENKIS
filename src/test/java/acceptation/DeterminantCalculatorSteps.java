package acceptation;

import com.example.exception.NoSquareException;
import com.example.model.Matrix;
import com.example.service.MatrixMathematics;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class DeterminantCalculatorSteps {

    private Matrix matrix;
    private double determinant;

    @Given("I have the matrix")
    public void iHaveTheMatrix(DataTable dataTable) {

        List<List<Double>> rows = dataTable.asLists(Double.class);


        matrix = new Matrix(rows.size(), rows.size());

        for(int i = 0; i< rows.size(); i++) {
            for(int j = 0; j< rows.get(i).size(); j++) {
                matrix.setValueAt(i, j, rows.get(i).get(j));
            }

        }


    }

    @When("I calculate the determinant")
    public void iCalculateTheDeterminant() throws NoSquareException {
        determinant = MatrixMathematics.determinant(matrix);
    }

    @Then("the result should be {int}")
    public void theResultShouldBe(int arg0) {

        assertEquals(arg0, determinant, 0.0001);

    }
}