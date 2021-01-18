import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2020.2"

project {
    description = "My Javascript project"

    vcsRoot(HttpsGithubComG0t4teamcityCourseCards)

    buildType(id02Firefox)
    buildType(id01FastTest)
    buildType(id02InternetExplorer)
    buildType(id02Chrome)
    buildType(id03DeployToStaging)

    template(Template_1)
}

object id01FastTest : BuildType({
    templates(Template_1)
    id("01FastTest")
    name = "01. Fast test"

    params {
        param("Browser", "PhantomJS")
    }
})

object id02Chrome : BuildType({
    templates(Template_1)
    id("02Chrome")
    name = "02 .Chrome"

    params {
        param("Browser", "Chrome")
    }

    dependencies {
        snapshot(id01FastTest) {
        }
    }
})

object id02Firefox : BuildType({
    templates(Template_1)
    id("02Firefox")
    name = "02. Firefox"

    params {
        param("Browser", "Firefox")
    }

    dependencies {
        snapshot(id01FastTest) {
        }
    }
})

object id02InternetExplorer : BuildType({
    templates(Template_1)
    id("02InternetExplorer")
    name = "02. Internet Explorer"

    params {
        param("Browser", "IE")
    }

    dependencies {
        snapshot(id01FastTest) {
        }
    }
})

object id03DeployToStaging : BuildType({
    id("03DeployToStaging")
    name = "03. Deploy To Staging"

    vcs {
        root(HttpsGithubComG0t4teamcityCourseCards)
    }

    dependencies {
        snapshot(id02Chrome) {
        }
        snapshot(id02Firefox) {
        }
        snapshot(id02InternetExplorer) {
        }
    }
})

object Template_1 : Template({
    id("Template")
    name = "template"

    vcs {
        root(HttpsGithubComG0t4teamcityCourseCards)
    }

    steps {
        script {
            name = "npm install"
            id = "RUNNER_5"
            scriptContent = "npm install"
        }
        script {
            name = "Browser tests"
            id = "RUNNER_6"
            scriptContent = "npm test -- --single-run --browsers %Browser% --colors false --reporters teamcity"
        }
    }
})

object HttpsGithubComG0t4teamcityCourseCards : GitVcsRoot({
    name = "https://github.com/g0t4/teamcity-course-cards"
    url = "https://github.com/g0t4/teamcity-course-cards"
    branch = "refs/heads/master"
})
