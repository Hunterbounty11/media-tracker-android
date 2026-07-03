# Week 6 Reflection

**Name:Hunter**
**Date:2 July 2026**

---

## Commits This Week



**Link:**
https://github.com/Hunterbounty11/media-tracker-android/commit/b60d1324ebc594b3854046e20d13e01f554eed81
---

## Code Review

**Reviewed:** Danny King **
**Link to my review:**
https://github.com/DannyKin/media-tracker-android/pull/5#pullrequestreview-4529737516
### What I Looked At
Danny's PR adds SearchScreen.kt, SearchViewModel.kt, SearchResultsScreen.kt, SearchComponents.kt, and searchResultsViewModel.kt.

### What I Noticed
He did a good job of following along with the lecture mostly, he is pretty much just as behind as I am, I am hoping once I get caught up this weekend I can help him implement search a bit better.


### Comments I Left
There was just a few things that could be cleaned up in resources right now. He no longer needs the string resource search_not_implemented 


---

## One Thing I Understood More Deeply.
This week really helped my understanding of the api constants. I was having issues where I was wondering why I consistently was getting null for the input, turns out I didnt have the local.properties under the root and instead had it inside of the app.
Another thing I had some issues about be imports into gradel build kits however I realized I was forgetting to rebuild it and that solved quite a few issues.
---

## One Thing I'm Still Confused About
The one thing I still am pretty confused about this week still has to be creating api calls. I didnt have time to fully work into the search function today, so I plan on exploring it more this weekend. I plan on doing another pull request this weekend and will update the reflection if progress is made. The thing I am still really struggling with is the posting of it though, finding out where I need to create the function was one of my big struggles this week 
---

## Anything Else *(optional)*
I really dont know if the format of this is off only on my side or on both. Some times when I type text like this its small other times it retains size from the header in front of it.


---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
