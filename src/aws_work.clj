(ns aws-work
  (:require
   [sensitive :refer [creds]]
   [clj-time.coerce :as t-coerce]
   [clj-time.core :as t]
   [com.rpl.specter :refer :all]
   [macros :refer :all]
   [amazonica.aws
    [ec2 :as ec2]
    [elasticloadbalancing :as elb]]))

;;; EC2
#_(-> (>>>) (setval! [:instances] (select [:reservations ALL :instances] (ec2/describe-instances creds))))

(spit "aws-resources.edn" (prn-str (aws ALL)))
(read-string (slurp "aws-resources.edn"))

#_(->> (aws :instances ALL)
       (select [(filterer [ :state :name (pred= "stopped") ]) ALL]))

#_(->> @(>>>)
       (select [:ec2 :instances :reservations ALL
                :instances
                (filterer :launch-time #(t/before? % (t-coerce/from-string "2016-07-23T20:30:29.000Z")))
                ALL
                :launch-time])
       )

;; => 33
;;; ELB
#_(-> (>>> :resources-atom)
      (setval! [:elb :elbs] (elb/describe-load-balancers)))


(def ^:const ec2-props
  #_(->> @(>>> :resources-atom)
         (select [:ec2 :instances :reservations ALL :instances ALL MAP-KEYS])
         (into #{}))
  #{:monitoring :tags :root-device-type :private-dns-name :ena-support
    :hypervisor :subnet-id :key-name :architecture :security-groups
    :source-dest-check :root-device-name :virtualization-type
    :elastic-gpu-associations :product-codes :instance-type
    :ami-launch-index :image-id :state :state-transition-reason
    :network-interfaces :vpc-id :ebs-optimized :instance-id :state-reason
    :iam-instance-profile :kernel-id :public-dns-name :private-ip-address
    :placement :client-token :public-ip-address :launch-time
    :block-device-mappings})
