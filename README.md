# HelloFresh JVM Take Home Test

Create an HTTP service for recording of statistics of some arbitrary data over a closed period of time.

* [`POST /event`](#post-event)
    * [Example Payload](#example-payload)
* [`GET /stats`](#get-stats)
    * [Example Response](#example-response)
* [Testing](#testing)
* [Requirements](#requirements)
    * [Bonus Requirement](#bonus-requirement)
* [Review Criteria](#review-criteria)

## `POST /event`

This route receives 3 values separated by a comma (`,`) where:

1. _timestamp_: An integer with the Unix timestamp in millisecond resolution when the event happened. The data is not
   ordered by this timestamp, this means that you may receive old data in any row.
1. 𝑥: A real number with a fractional part of up to 10 digits, always in 0..1.
1. 𝑦: An integer in 1,073,741,823..2,147,483,647.

The response should be [202](https://httpstatuses.com/202) if the data was successfully processed. Choose appropriate
status codes for other circumstances, also think about what to do if some rows are valid and others are not.

### API SWAGGER ENDPOINT

```
http://localhost:8080/swagger-ui/index.html#/
```

![img.png](img.png)
