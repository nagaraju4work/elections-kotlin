Android Technical Assessment
---

This is a mobile client for a mock elections system which could show results pages similar to how BBC News covers elections. 
**Note:** If you are not familiar with how elections work in the UK, please see this short BBC video https://www.youtube.com/watch?v=cRxUhGetEPQ


## Setup

Requires:

- Android Studio Chipmunk or higher
- [Zoom](https://zoom.us/) or Teams for screensharing

Notes:

- In this directory there is a single Android project containing two separate application modules, `appWithComposeUI` and `appWithXmlUI`. **You must choose whether to use Jetpack Compose or XML layouts, and use the appropriate module to complete the assessment.**
- The project also includes an `api` module containing shared code used by both targets. As part of completing the tasks, you may make changes inside this module if you wish.
- All targets also include unit tests, which can be run by selecting the correct folder in the project hierarchy menu, right clicking and choosing _'Run Tests in...'_.

**Please verify that you can open the project in Android Studio, build the application, and run the unit tests in advance of your booked assessment time.**

## Assessment Time: 3 Days

## Assessment Process

In the assessment process, after making changes in your github repository, we will ask you to share your working environment and talk through the following with us in a recorded video (shared via youtube link (uploaded as 'PRIVATE and reachable only via link' may be):

- What does this system do? What are its key features?
- New Feature requests! - The election product team have some feature requests that we would like you to take a look at. They can be found in `Tasks.md`.
- Please create a github repository of your own and share your assignment as a repository, detail the code changes in readme file and share the youtube link within readme.

If you have any problems with any of the above, please get in touch via your recruitment contact.

Please feel free to send the assessment response as a link via recruitment contact


## Tasks done by Nagaraju T

1. I have utilized the flag isComplete as true after all iterations to mark the elections as done. Hidden the floating button when isComplete flag is true and highlighted the Winner with odd color background and a winner tag. Calculated the winner by using maxBy which will help to change the Winner on each iteration.
2. I have called the all candidates api to fetch the names and mapped them to the Results where I have candidate ID. So Candidate Names are displayed now.
3. Talkback provides spoken feedback and navigation instructions to make interacting with the device's interface more accessible. Having the Header may confuse the Visually impaired, hence Header doesn't required so Removed it. Also while speaking, to to make the details more clear for the Visually Impaired all the details having quoted with respective params.
4. According to WCAG (Web Content Accessibility Guidelines), the recommended touch target size is 48dp x 48dp. Here the TextViews have been adjusted to 48dp height while the width adjusted as per the text.
5. Implemented the ConnectivityHelper for knowing the connectivity exists or not and a helper function to display No Internet alert. Also added NetworkCallback functionality where I randomly enable/Disable the connectivity and able to see the error messages on top of the screen.
6. Finally, Test Cases written were modified as My ViewModel function has been modified to fetch the candidate names too. All Test cases are executed successfully.
