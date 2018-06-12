# Webstep IOT DT Java Start

Simple Java example of using the Disruptive Technologies (DT) REST API.

The example basically does the same as the Python example provided in the DT documentation, creating or fetching a virtual touch button and sending touch events to it every second until quit. HTTP GET and POST are demonstrated with key ID + Secret using a service account.

To run the example you need a service account key and secret as well as the project key, this should be provided at the hackathon. 

The example uses only classes that are shipped with the Java JDK. JDK 8 was used when created.

### Setup
You need to aquire and enter projectId, serviceAccountKey and serviceAccountSecret in TouchTrigger.java. The service account must have at least Developer rights (developer or admin).

## TouchTrigger
### Compiling
Simply type 'javac TouchTrigger.java'

### Running
Simply type 'java TouchTrigger'