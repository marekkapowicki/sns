package contracts


import org.springframework.cloud.contract.spec.Contract

Contract.make {

    description "should push the notification"

    request {
        method("POST")
        url("/api/notifications")
        headers {
            contentType(applicationJson())
        }
        body(file("notification_request.json"))
    }

    response {
        status 200
        headers {
            contentType("application/json")
        }
    }

}