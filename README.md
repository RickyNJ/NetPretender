#Network device mocking
This is the application that will be running inside a docker container you can ssh into

The following features will be (hopefully) implemented:

    [ ] Yaml file parsing to store mocks in code

    [ ] Custom response file to keep longer responses out of the yaml

    [ ] Variables in commands

    [ ] Variables in output

    [ ] Output customization (delays, and outputting in multiple parts)

    [ ] Internal State of a machine

    [ ] Referencing to internal state in output

    [ ] Conditional outputs based on varialbes and internal state

Notes to myself:
No need to do difficult two step search if new node is required.
just reserve ${} and create var node, commands are split based on words so you can still match on that base.

Add more complexity in terms of conditional responses. Think of specific responses for specific inputs only?
maybe this should be in a seperate mock, but the use case of 1 command with  3 possible responses for different values seems more readable as 1 mock.

Look into deploying this docker container into gns3.
Do more gns3 research in general.

How to implement variables in nodes