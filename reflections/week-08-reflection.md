# Week 8 Reflection

**Name:Hunter**
**Date:9 July 2026**

---

## Commits This Week



**Link:**
https://github.com/Hunterbounty11/media-tracker-android/pull/8/changes/dcb90271722d2a30bd74c33d1b94cd59c170f392


## Code Review

**Reviewed:** Khalid Hassan **
**Link to my review:**
https://github.com/Khalid-H634/media-tracker-android/pull/8#pullrequestreview-4668052468

### What I Looked At
I looked at his search results screen and the search results data model.
### What I Noticed
He has a bunch of hard coded references to the fake media repository instead of a reference that he just has to change one.(So when changing to api he has to hunt down all of the references to fake media repository instead of just the one)


### Comments I Left
I tried to leave comments on all of the places I found the FakeMediaRepository references. And recommended he goes with one val that he can just change up at the top of the file for easy changes
---

## One Thing I Understood More Deeply.
This week greatly enhanced my understanding of how the api works. The fact that I was just missing the serializable on the Media class leading to a significant search today. Going forward it is hopefully something I learn to do to anything moving over network(I think that is when you have to use serializable if what I was reading was correct). I already did it on reviews as soon as I started moving to use the API to call them.
---

## One Thing I'm Still Confused About
I am going to have to research why my app is stuck in capslock. Not a big deal and hasn't effected anything but it would be a nice to know. Also I need to look into why no reviews are actually coming through. All media results are showing no reviews for now.

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
