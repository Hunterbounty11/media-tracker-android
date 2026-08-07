# Week 11 Reflection — Bonus Feature Sprint (Week 1 of 2)

*This week's reflection is different from the standard template. We're not doing Profile this week — instead, this is the first of two weeks building your assigned bonus feature (Write Review, Quotes, or Priorities). See `reflection-instructions.md` for naming/submission rules, which are unchanged; only the content below differs.*

**Name:Hunter Bammert-Mueller**
**Date:31 July 2026**
**My assigned bonus feature:** *Priorities*

---

## Commits This Week


**Link:**
https://github.com/Hunterbounty11/media-tracker-android/commit/82148a05cd9b6f0fa2277fcb83075ad6b695673b
---

## Code Review

**Reviewed:** *Danny King*
**Link to my review:**
https://github.com/DannyKin/media-tracker-android/pull/10/changes/BASE..95ff10639ea85a01be5c3705f29de292a08fc0fa
### What I Looked At
Because his assigned feature was quotes I tried to follow that train while looking at it.

### What I Noticed
There were a couple of changes that was weird to me. Biggest one was that he removed the getReviews inside of the DefaultMediaRepository. I can't see a reason for that.

### Comments I Left
The main commment I left was around why the reviews were removed.
---

## Bonus Feature Progress

<!-- This is the most important section this week. Be concrete: which endpoint(s) did you wire?
     What's actually showing on screen with real data? What's still stubbed or fake?
     "I worked on my bonus feature" is not an answer. "I got POST /quotes working from Media Detail
     and quotes show up in a list on my profile, but I haven't wired edit or delete yet" is. -->

**What's working:**
---
All of week 1 was working.This includes PUT /priorities, GET /priorities, the data model for priorities and also the basic list

**What's still stubbed, fake, or not started:**
---
I didn't have a 5 priority limit. Also drag and drop was not working nor was the reorder.
**What I'm blocked on, if anything:**
---
Nothing really for that.
## One Thing I Understood More Deeply

<!-- Be specific. What clicked this week, building your own feature instead of following a handout step-by-step? -->

---
The one thing that really clicked for me this week was making sure my serialization and data model were solid before actually trying to call them. Making sure that that is right really lowers the effort when using it.
## One Thing I'm Still Confused About

<!-- Be honest. This tells me where to spend time in class next week. -->
---
I am still pretty confused on the difference between PUT and POST.

## Anything Else *(optional)*

---
I am trying to do this retroactively, I did it last week, but it appears to not have saved. I also decided to run linux so the drive got wiped...

On the other hand I haven't done much writing in md files. I just found out why my formating always seemed messed up. It's because those three dashes I thought was to separate sections actually meant something
## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Concrete progress report (what's wired, what's not) plus specific, honest "Understood More Deeply" and "Still Confused" sections. | Present but vague — "I worked on my feature" with no specifics on what's actually working. | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match.
