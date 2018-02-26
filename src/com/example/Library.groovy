package com.example

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
            successfulBuilds.put(build.number, getCommitHash(build).substring(0, 7)) // Short Version of the Hash
        }
        lastSuccessfullBuild(build.getPreviousBuild(), successfulBuilds)
    }else {
      successfulBuilds
    }
 }

//Reference https://gist.github.com/ftclausen/8c46195ee56e48e4d01cbfab19c41fc0
@NonCPS
private def getCommitHash(build) {
  def rawBuild = currentBuild.rawBuild
  def scmAction = rawBuild?.actions.find { action -> action instanceof jenkins.scm.api.SCMRevisionAction }
  scmAction?.revision?.hash
}
