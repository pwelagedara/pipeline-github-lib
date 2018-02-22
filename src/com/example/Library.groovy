package com.example

def getCommitHash(){
  sh "git rev-parse --short HEAD > commit-hash.txt"
  return readFile('commit-hash.txt').trim()
}
