package com.example

def getCommitHash() {
  sh "git rev-parse --short HEAD > commit-hash.txt"
  readFile('commit-hash.txt').trim()
}

def getCurrentBranch() {
  sh "git rev-parse --abbrev-ref HEAD > current-branch.txt"
  readFile('current-branch.txt').trim()
}
