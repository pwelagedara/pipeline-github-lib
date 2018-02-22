package com.example

def getCommitHash(){
  sh "git rev-parse --short HEAD > commit-hash.txt"
  readFile('commit-hash.txt').trim()
}
