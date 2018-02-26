package com.example

// Add Successful Builds to a Map and return
def getSuccessfulBuildsMap(currentBuild) {
  def successfulBuilds = lastSuccessfullBuild(currentBuild.getPreviousBuild(), [:])
  println 'sizeeee'
  println successfulBuilds.size()
  return successfulBuilds
}

// Reference https://support.cloudbees.com/hc/en-us/articles/217591038-How-to-Iterate-Through-the-Last-Successful-Builds-in-Pipeline-Job
def lastSuccessfullBuild(build, successfulBuilds) {
    if(build != null){
        if(build.result == 'SUCCESS') {
            println 'okayyyyyyyyy'
            successfulBuilds.put(build.number, getCommitHash(build))
        }
        lastSuccessfullBuild(build.getPreviousBuild(), successfulBuilds)
    }else {
      return successfulBuilds
    }
 }

//Reference https://gist.github.com/ftclausen/8c46195ee56e48e4d01cbfab19c41fc0
@NonCPS
def getCommitHash(build) {
  def rawBuild = currentBuild.rawBuild
  def scmAction = rawBuild?.actions.find { action -> action instanceof jenkins.scm.api.SCMRevisionAction }
  return scmAction?.revision?.hash
}
