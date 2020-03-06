# Clojure(Script) training with Metosin

## EXAMPLE PROJECT

Example project for an "event app" can be found in `event-app` branch.

## Before the training

### Register accounts

#### OpenWeather API

1. Sign up for account: https://home.openweathermap.org/users/sign_up
2. Click the link that you received in your email. Activating the API key may take a few hours after confirming your email.
3. Get API key: https://home.openweathermap.org/api_keys

#### Heroku

1. Register to Heroku: https://www.heroku.com/
2. Create app with Heroku Git, e.g., `clojure-training`
3. Select buildpack `heroku/clojure`. Can be found in *Buildpacks* under project *Settings* tab.
4. Add resource for Heroku Postgres, Hobby Dev tier. Can be found in *Add-ons* under *Resources*.
5. See command line (CLI) installation below

### Install command line tools

At this point, we ask you to install a bunch of tools without too many questions. It will all make sense later, when we go through these and use them in practise.

If you already have working tools for some of these, please check that they are not terribly outdated.

- Java and JDK
  * Check that you have at least Java 8 on command line with `java --version`
  * If needed, install Java. OpenJDK works well: [https://openjdk.java.net/install/](https://openjdk.java.net/install/)
- Clojure CLI tools
  * [https://clojure.org/guides/getting_started#_clojure_installer_and_cli_tools](https://clojure.org/guides/getting_started#_clojure_installer_and_cli_tools)
  * Verify that `clj` on command line opens a Clojure prompt. Close with Ctrl+D.
- Leiningen
  * [https://leiningen.org/](https://leiningen.org/)
  * Check that Leiningen is in the path with `lein --version`
- Git
  * Install: [https://git-scm.com/book/en/v2/Getting-Started-Installing-Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
  * Configure your identity and editor: [https://git-scm.com/book/en/v2/Getting-Started-First-Time-Git-Setup](https://git-scm.com/book/en/v2/Getting-Started-First-Time-Git-Setup)
- Heroku CLI
  * Requires Git to be installed first
  * [https://devcenter.heroku.com/articles/heroku-cli#download-and-install](https://devcenter.heroku.com/articles/heroku-cli#download-and-install)
- Docker and Docker Compose
  * Mac or Windows: [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop)
  * Linux, Docker: [https://docs.docker.com/install/](https://docs.docker.com/install/)
  * Linux, Docker Compose: [https://docs.docker.com/compose/install/](https://docs.docker.com/compose/install/)
- Node.js & NPM/NPX
  * [https://nodejs.org/en/download/](https://nodejs.org/en/download/)

### Setup IDE

The important features that you should look for in Clojure IDE are:
- REPL, the IDE should allow access to project REPL
- Evaluating from source, you should be able to select part of source code and send it to REPL for evaluation

#### IntelliJ IDEA (strongly recommended)

Content of the training will be demonstrated using IntelliJ IDEA with Cursive (Clojure) plugin. We are able to offer limited support for other IDEs and configurations. 

- Community Edition (free) version of IntelliJ IDEA works well in Clojure development.
  * [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
- Cursive plugin has a free trial of 30 days.
  * See installation instructions: [https://cursive-ide.com/userguide/](https://cursive-ide.com/userguide/)

#### VS Code 

Calva is a decent and active environment for Clojure and ClojureScript development. See [https://marketplace.visualstudio.com/items?itemName=betterthantomorrow.calva](https://marketplace.visualstudio.com/items?itemName=betterthantomorrow.calva)

#### Emacs

Cider is the de-facto tool for CLJ(S) development in Emacs. See [https://github.com/clojure-emacs/cider](https://github.com/clojure-emacs/cider)

#### Eclipse

Counterclockwise plugin provides Clojure support. It is not commonly used and has had problems with version compatibility. See [https://marketplace.eclipse.org/category/free-tagging/counterclockwise](https://marketplace.eclipse.org/category/free-tagging/counterclockwise). 

### Setup interactive REPL in IntelliJ IDEA

1. Checkout this Git repository on your own computer. Press the green `Clone or download` button in Github UI, and copy the repository URI. Open command line and enter `git clone` followed by the repository URI. This will create a subdirectory in the current location of your command line.
2. Open IntelliJ IDEA and choose `Import project`. 
3. Select `Import project from external sources` -> `Leiningen`
  - Do NOT import projects recursively
4. When IDEA asks to select project SDK, check that a valid Java install can be found. See above for installing Java JDK.
5. Go to `Settings` ->  `Clojure` and select `Evaluate forms in REPL namespace`
6. Go to `Settings` -> `Plugins` -> `Cursive`
  - Check that you have usable keybindings for `Send form before caret to REPL` and `Send top form to repl`
7. In top right of the IDE view, click on `Add Configuration...`
8. Press the plus icon to open a popup menu with title `Add New Configuration`.
  - It’s an easy mistake to edit templates directly, as the path is the same as below. 
9. Select `Clojure REPL` -> `Local`
10. Give a descriptive name for the REPL. For present purposes, you may want to include `Backend` in the name.
11. Check that following are selected: `nREPL` and `Run with Leiningen`, 
12. Click OK to create this new launch configuration.
13. Press "PLAY" icon to start the REPL.
14. After REPL opens, top part is the read-only output. Type into the bottom part `(+ 1 1)`. You should get `=> 2` as a result.
15. Open a source file in IDEA. There is a Clojure file under `src/main`. 
16. Move the caret to inside the `(comment ...)` block at bottom. Place the caret after the closing bracket of form `(hello "Morty Smith")`.
17. Use the keyboard shortcut assigned to `Send form before caret to REPL`. The form will be sent to REPL and evaluated. 
18. Move the caret after last quote in `"Morty Smith"`. Try and see what is the difference between keyboard shortcuts `Send form before caret to REPL` and `Send top form to repl`.

### Setup Heroku connection

1. Add Heroku project’s Git repository as remote:
  - `heroku git:remote -a clojure-training`
2. Deploy the example app located in `heroku-test` branch to Heroku
  - `git push heroku heroku-test:master`
3. Open the URL shown in response to view the deployed Clojure web app
  - Check the root path `/` for generic message.
  - Call `/add` with query params `a` and `b` to get dynamic response, e.g., `/?a=5&b=37`.
4. Revert to local Git master in Heroku
  - `git push heroku master -f`
  - NOTE: This time only, we are using `-f` flag to force-push to the remote repository. Usually you want to avoid force-pushing, especially when you have a shared repository.
5. The app crashes, as the `master` branch does not have a valid Heroku configuration. Check the cause of the crash from the logs.
  - `heroku logs -t`
