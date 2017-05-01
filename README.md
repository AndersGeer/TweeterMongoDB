# TweeterMongoDB
Tweets assignment for Database course on CPHBusiness

Note: Not everything works in this java version.
___
How many Twitter users are in our database?

There are **659774** distinct users in our database. 
`db.tweets.distinct("user").length`
___
Which Twitter users link the most to other Twitter users? (Provide the top ten.)

*Not finished*
**Note: This mentions linking to other users, and is based on finding twitter URLS**

```db.tweets.aggregate(
[
    {$match:{text:/http:\/\/twitter.com\/\w+/}},
    {$group:{_id:"$user" ,text:{$push:"$text"}}}
],
{
    allowDiskUse: true
}
)
```

This does however find a few false positives, such as http://twitter.com/help
___
Who is are the most mentioned Twitter users? (Provide the top five.)

*Not finished*
```
db.tweets.aggregate(
[
    {$match:{text:/@\w+/}},
    {$group:{_id:"$id",text:{$push:"$text"}}}
],
{
    allowDiskUse: true
}
)
```
___
Who are the most active Twitter users (top ten)?

*Not finished*
Have yet to find a proper way of counting posts, this groups them based on user posting
```db.tweets.aggregate(
[
    {$match:{}},
    {$group:{_id:"$user",text:{$push:"$text"}}}
],
{
    allowDiskUse: true
}
)
```
___
Who are the five most grumpy (most negative tweets) and the most happy (most positive tweets)? (Provide five users for each group)

*Not finished*
Find an avg of polarity of the users post and show highest 5 and or lowest 5.
Highest 5 appears to be the same, as these users only has 1 post each of polarity each. (Maybe try to get avg of a minimum of posts?)

```db.tweets.aggregate(
[
    {$match:{}},
    {$group:{_id:"$user",text:{$avg:"$polarity"}}}
],
{
    allowDiskUse: true
}
)
```

___
