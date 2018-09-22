#!/bin/bash
echo "Moving Quizzes"
cp QuizPlugins/DoctorWhoQuiz/build/libs/DoctorWhoQuiz.jar plugins/Quizzes/
echo "Moving Question Types"
cp QuestionTypes/MultiChoice/build/libs/MultiChoice.jar plugins/QuestionTypes/
cp QuestionTypes/ShortAnswer/build/libs/ShortAnswer.jar plugins/QuestionTypes/
echo "Running program"
java -jar build/libs/janeks-TextGame.jar
