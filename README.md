# CodeBogo-BE
코드보고 백엔드

Code-Bogo/
├─ build.gradle
├─ settings.gradle
├─ gradlew
├─ gradlew.bat
├─ .gitignore
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ com/
│  │  │     └─ example/
│  │  │        └─ codebogo/
│  │  │           ├─ CodeBogoApplication.java
│  │  │           │
│  │  │           ├─ global/
│  │  │           │  ├─ config/
│  │  │           │  │  ├─ SwaggerConfig.java
│  │  │           │  │  └─ WebConfig.java
│  │  │           │  └─ exception/
│  │  │           │     ├─ CustomException.java
│  │  │           │     └─ GlobalExceptionHandler.java
│  │  │           │
│  │  │           ├─ user/
│  │  │           │  ├─ controller/
│  │  │           │  │  └─ UserController.java
│  │  │           │  ├─ dto/
│  │  │           │  │  └─ response/
│  │  │           │  │     └─ UserStatusResponse.java
│  │  │           │  ├─ entity/
│  │  │           │  │  └─ User.java
│  │  │           │  ├─ repository/
│  │  │           │  │  └─ UserRepository.java
│  │  │           │  └─ service/
│  │  │           │     └─ UserService.java
│  │  │           │
│  │  │           ├─ problem/
│  │  │           │  ├─ entity/
│  │  │           │  │  ├─ Problem.java
│  │  │           │  │  └─ AnswerOption.java
│  │  │           │  └─ repository/
│  │  │           │     └─ ProblemRepository.java
│  │  │           │
│  │  │           ├─ history/
│  │  │           │  ├─ entity/
│  │  │           │  │  └─ ProblemHistory.java
│  │  │           │  └─ repository/
│  │  │           │     └─ ProblemHistoryRepository.java
│  │  │           │
│  │  │           └─ quiz/
│  │  │              ├─ controller/
│  │  │              │  └─ QuizController.java
│  │  │              ├─ dto/
│  │  │              │  ├─ request/
│  │  │              │  │  └─ SubmitAnswerRequest.java
│  │  │              │  └─ response/
│  │  │              │     ├─ StartQuizResponse.java
│  │  │              │     ├─ CurrentProblemResponse.java
│  │  │              │     ├─ SubmitAnswerResponse.java
│  │  │              │     ├─ NextQuestionResponse.java
│  │  │              │     └─ QuizResultResponse.java
│  │  │              ├─ entity/
│  │  │              │  ├─ QuizSession.java
│  │  │              │  ├─ QuizSessionProblem.java
│  │  │              │  └─ QuizStatus.java
│  │  │              ├─ repository/
│  │  │              │  ├─ QuizSessionRepository.java
│  │  │              │  └─ QuizSessionProblemRepository.java
│  │  │              └─ service/
│  │  │                 ├─ QuizSessionService.java
│  │  │                 └─ QuizSessionProblemService.java
│  │  │
│  │  └─ resources/
│  │     ├─ application.yml
│  │     └─ static/
│  │
│  └─ test/
│     └─ java/
│        └─ com/
│           └─ example/
│              └─ codebogo/
│                 └─ CodeBogoApplicationTests.java
│
└─ build/
