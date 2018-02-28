#! /usr/bin/groovy

package com.example


def setEnvironmentVariables() {
  if (env.ENVIRONMENT == 'qa' && env.BRANCH_NAME == 'dev') { // Dev Branch always goes to qa
    // None of the variables need to be changed
  }else if(env.ENVIRONMENT == 'staging' && env.BRANCH_NAME.startsWith("release-")) { // Release Branch always goes to staging
    // None of the variables need to be changed
  } else if () { // PRs will skip stages from Dry Run onwards because the app is already in qa and staging
        env.SKIP_STAGE_PUBLISH=true
        env.SKIP_STAGE_DRY_RUN=true
        env.SKIP_STAGE_DEPLOY=true
  } else {
    // Skip nothing
  }
}

// Add Successful Builds to a Map and return
/* Needs the following scripts to be approved
*  method hudson.model.Actionable getActions
*  method jenkins.plugins.git.AbstractGitSCMSource$SCMRevisionImpl getHash
*  method jenkins.scm.api.SCMRevisionAction getRevision
*  method org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper getRawBuild
*/
def getSuccessfulBuildsMap(currentBuild) {
  lastSuccessfullBuild(currentBuild.getPreviousBuild(), [:])
}

// Reference https://support.cloudbees.com/hc/en-us/articles/217591038-How-to-Iterate-Through-the-Last-Successful-Builds-in-Pipeline-Job
private def lastSuccessfullBuild(build, successfulBuilds) {
    if(build != null){
        if(build.result == 'SUCCESS') {
            def commitHash = getCommitHash(build.rawBuild).substring(0, 7) // Short Version of the Hash
            successfulBuilds.put(build.number, commitHash)
        }
        lastSuccessfullBuild(build.getPreviousBuild(), successfulBuilds)
    }else {
      successfulBuilds
    }
 }

//Reference https://gist.github.com/ftclausen/8c46195ee56e48e4d01cbfab19c41fc0
@NonCPS
private def getCommitHash(rawBuild) {
  def scmAction = rawBuild?.actions.find { action -> action instanceof jenkins.scm.api.SCMRevisionAction }
  scmAction?.revision?.hash
}
