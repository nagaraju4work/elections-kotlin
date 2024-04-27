# Mobile Assessment Tasks

The product owner has asked us to use to add some additional features to the application. They are unfortunately not available to clarify the below so we are being trusted in our interpretation of them!

## Features

### 1) There is no way to tell when the counting has finished and a winner has been declared.

- When counting is complete can we remove the refresh button?
- Can we make it clear when the counting is complete?
- Can we calculate the winner and highlight them in the table somehow?

### 2) There are no candidate names!

- I'd like to see the candidate names added to the UI. The candidate ID numbers are meaningless to the audience.

### 3) This UI doesn't work well with TalkBack. What can we do to improve accessibility?

- The party, candidate name and votes fields need to be stepped through individually. Can we make each row a single element which contains all the relevant information?
- The table headings (Party, Candidate, Votes) aren't very meaningful for TalkBack users, could we hide them?

### 4) This UI doesn't work well with TouchTargets. What can we do to improve touch target according to WCAG?

-  The candidate name and votes fields to be checked to align to international standards on touch target-so it helps user with cerebral palsy to access the app. How to enable appropriate touch target?

### 5) This UI doesn't work well with Error Messages. How to improve error elerts?

- If internet is not connected or server is down, send alert via app to user when app is active. How to get this message displayed (and get it disappeared when internet is back) with right colour background and close button?
