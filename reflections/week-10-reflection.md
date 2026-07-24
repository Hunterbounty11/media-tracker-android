# Week 10 Reflection

**Name:Hunter**
**Date:23 July 2026**

---

## Commits This Week



**Link:**
https://github.com/Hunterbounty11/media-tracker-android/pull/9/changes/abed0d9a59b168fbeae98e5e460f5c057902576f


## Code Review

**Reviewed:** Danny King **
**Link to my review:**
https://github.com/DannyKin/media-tracker-android/pull/9#pullrequestreview-4769639230
### What I Looked At
I took a look at the library api calls and the library screen as that is where most of the focus was on this class
### What I Noticed
He did a great job going back and addressing the missing icons. He now is using a function instead of checking and linking it directly in the files.
He is also missing a serializable attached to the library status. 

### Comments I Left
I pointed out where his serializable was missing
---

## One Thing I Understood More Deeply.
The data flow of where exactly data is being passed and how it is stored really clicked for me today. The default repositories(Both user and session)that store the result of the api calls that then the rest of the app uses to interact with instead of each page doing its own thing.

---

## One Thing I'm Still Confused About
I am still looking into this because I thought everything should be working, but I cannot get the Library Items to stay persistant. I believe I am adding it to the favorites from media details, however it is not appearing inside of library. I believe right now that it is a problem with my api calls or it could be i am not properly storing it in view models. I am going to be trying to fix that this weekend.


---

## Anything Else *(optional)*

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
