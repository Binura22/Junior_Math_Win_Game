import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import com.example.juniormathwin.R
import com.example.juniormathwin.ViewModel.ProblemsViewData
import com.example.juniormathwin.performance.EasyProblemGenerator
import com.example.juniormathwin.performance.HardProblemGenerator
import com.example.juniormathwin.performance.MediumProblemGenerator
import com.example.juniormathwin.problemsView
import com.example.juniormathwin.scoreView

class ProblemChecker {

    fun checkAnswer(selectedAnswer: Int, activity: problemsView, viewModel: ProblemsViewData) {
        //variable declaration
        val num1 = activity.findViewById<TextView>(R.id.number1).text.toString().toInt()
        val num2 = activity.findViewById<TextView>(R.id.number2).text.toString().toInt()
        val operator = activity.findViewById<TextView>(R.id.operationSign).text.toString()

        val correctAnswer = when (operator) {
            "+" -> {
                num1 + num2
            }
            "-" -> {
                num1 - num2
            }
            "×" -> {
                num1 * num2
            }
            else -> {
                num1 / num2
            }
        }
        //check user selected answer
        if (selectedAnswer == correctAnswer) {
            activity.timer.cancel()
            //generate toast message if the user correct
            Toast.makeText(activity, "You're correct!", Toast.LENGTH_SHORT).show()
            //increment problem counter
            activity.problemsCounter++
            //store remaining time
            val remainingTime = activity.findViewById<TextView>(R.id.time).text.toString().toLong()
            //calculate the score based on remaining time
            val currentScore = viewModel.score.value ?: 0
            val newScore = currentScore + (remainingTime * 10).toInt()
            viewModel.setScore(newScore)
            //display current score in score textview
            activity.findViewById<TextView>(R.id.score).text = "Score: $newScore"

            //if the user answer to 10 question pass score to scoreView
            if (activity.problemsCounter >= 10) {
                val intent = Intent(activity, scoreView::class.java)
                intent.putExtra("score", newScore)
                //pass current level to score view
                val currentLevel = activity.intent.getStringExtra("level")
                intent.putExtra("level", currentLevel)
                activity.startActivity(intent)
            } else {
                //generate next problem based on level
                val level = activity.intent.getStringExtra("level")
                if (level == "Easy") {
                    val problemGenerator = EasyProblemGenerator(activity)
                    problemGenerator.generateAndDisplayProblem(viewModel)
                } else if (level == "Medium") {
                    val problemGenerator = MediumProblemGenerator(activity)
                    problemGenerator.generateAndDisplayProblem(viewModel)
                } else {
                    val problemGenerator = HardProblemGenerator(activity)
                    problemGenerator.generateAndDisplayProblem(viewModel)
                }
            }
        } else {
            //reduce score for wrong answers
            val currentScore = viewModel.score.value ?: 0
            val newScore = if (currentScore <= 0) 0 else currentScore - 100
            viewModel.setScore(newScore)

            activity.findViewById<TextView>(R.id.score).text = "Score: $newScore"
            //display toast message if the user selected answer is incorrect
            Toast.makeText(activity, "Incorrect answer. Try again!", Toast.LENGTH_SHORT).show()
        }
    }
}
