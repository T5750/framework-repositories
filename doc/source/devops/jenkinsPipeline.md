# Jenkins Pipeline

## Ansi Color Build Wrapper
```
// This shows a simple build wrapper example, using the AnsiColor plugin.
node {
    // This displays colors using the 'xterm' ansi color map.
    ansiColor('xterm') {
        // Just some echoes to show the ANSI color.
        stage "\u001B[31mI'm Red\u001B[0m Now not"
    }
}
```

## Archive Build Output Artifacts
```
// This shows a simple example of how to archive the build output artifacts.
node {
    stage "Create build output"
    // Make the output directory.
    sh "mkdir -p output"
    // Write an useful file, which is needed to be archived.
    writeFile file: "output/usefulfile.txt", text: "This file is useful, need to archive it."
    // Write an useless file, which is not needed to be archived.
    writeFile file: "output/uselessfile.md", text: "This file is useless, no need to archive it."
    stage "Archive build output"
    // Archive the build output artifacts.
    archiveArtifacts artifacts: 'output/*.txt', excludes: 'output/*.md'
}
```

## Parallel From List
```
// While you can't use Groovy's .collect or similar methods currently, you can
// still transform a list into a set of actual build steps to be executed in parallel.
// Our initial list of strings we want to echo in parallel
def stringsToEcho = ["a", "b", "c", "d"]
// The map we'll store the parallel steps in before executing them.
def stepsForParallel = stringsToEcho.collectEntries {
    ["echoing ${it}" : transformIntoStep(it)]
}
// Actually run the steps in parallel - parallel takes a map as an argument, hence the above.
parallel stepsForParallel
// Take the string and echo it.
def transformIntoStep(inputString) {
    // We need to wrap what we return in a Groovy closure, or else it's invoked
    // when this method is called, not when we pass it to parallel.
    // To do this, you need to wrap the code below in { }, and either return
    // that explicitly, or use { -> } syntax.
    return {
        node {
            echo inputString
        }
    }
}
```

## Parallel Multiple Nodes
```
def labels = ['precise', 'trusty'] // labels for Jenkins node types we will build on
def builders = [:]
for (x in labels) {
    def label = x // Need to bind the label variable before the closure - can't do 'for (label in labels)'
    // Create a map to pass in to the 'parallel' step so we can fire all the builds at once
    builders[label] = {
        node(label) {
            // build steps that should happen on all nodes go here
        }
    }
}
parallel builders
```

## Timestamper Wrapper
```
// This shows a simple build wrapper example, using the Timestamper plugin.
node {
    // Adds timestamps to the output logged by steps inside the wrapper.
    timestamps {
        // Just some echoes to show the timestamps.
        stage "First echo"
        echo "Hey, look, I'm echoing with a timestamp!"
        // A sleep to make sure we actually get a real difference!
        stage "Sleeping"
        sleep 30
        // And a final echo to show the time when we wrap up.
        stage "Second echo"
        echo "Wonder what time it is now?"
    }
}
```

## References
- [Pipeline Examples](https://jenkins.io/doc/pipeline/examples/)