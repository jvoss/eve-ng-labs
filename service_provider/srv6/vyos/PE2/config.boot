interfaces {
    ethernet eth0 {
        description "P3"
    }
    ethernet eth1 {
        address "172.16.12.2/24"
        address "fd01:101:12::2/64"
        description "CE1"
        vrf "VRF1"
    }
    ethernet eth2 {
    }
    ethernet eth3 {
    }
    loopback lo {
        address "2001:db8:2:ffff::2/128"
    }
}
protocols {
    bgp {
        neighbor 2001:db8:4:ffff::4 {
            description "PE4"
            peer-group "PE"
        }
        parameters {
            router-id "10.0.0.2"
        }
        peer-group PE {
            address-family {
                ipv4-vpn {
                    nexthop-self
                }
                ipv6-vpn {
                    nexthop-self
                }
            }
            capability {
                extended-nexthop
            }
            remote-as "internal"
            update-source "lo"
        }
        srv6 {
            locator "L3VPN"
        }
        system-as "65000"
    }
    isis {
        interface eth0 {
        }
        interface lo {
        }
        level "level-2"
        net "49.0000.2222.2222.2222.00"
        redistribute {
            ipv6 {
                static {
                    level-2
                }
            }
        }
    }
    segment-routing {
        interface eth0 {
            srv6
        }
        srv6 {
            locator L3VPN {
                behavior-usid
                prefix "2001:db8:2:aaa::/64"
            }
        }
    }
    static {
        route6 2001:db8:2::/48 {
            blackhole
        }
    }
}
service {
    ntp {
        allow-client {
            address "0.0.0.0/0"
            address "::/0"
        }
        server time1.vyos.net {
        }
        server time2.vyos.net {
        }
        server time3.vyos.net {
        }
    }
}
system {
    config-management {
        commit-revisions "100"
    }
    conntrack {
        modules {
            ftp
            h323
            nfs
            pptp
            sip
            sqlnet
            tftp
        }
    }
    console {
        device ttyS0 {
            speed "115200"
        }
    }
    host-name "PE2"
    login {
        user vyos {
            authentication {
                encrypted-password "$6$QxPS.uk6mfo$9QBSo8u1FkH16gMyAVhus6fU3LOzvLR9Z9.82m3tiHFAxTtIkhaZSWssSgzt4v4dGAL8rhVQxTg0oAG9/q11h/"
                plaintext-password ""
            }
        }
    }
    sysctl {
        parameter net.vrf.strict_mode {
            value "1"
        }
    }
    syslog {
        global {
            facility all {
                level "info"
            }
            facility local7 {
                level "debug"
            }
        }
    }
}
vrf {
    name VRF1 {
        protocols {
            bgp {
                address-family {
                    ipv4-unicast {
                        export {
                            vpn
                        }
                        import {
                            vpn
                        }
                        rd {
                            vpn {
                                export "10.0.0.2:101"
                            }
                        }
                        redistribute {
                            connected
                        }
                        route-target {
                            vpn {
                                export "65000:101"
                                import "65000:101"
                            }
                        }
                    }
                    ipv6-unicast {
                        export {
                            vpn
                        }
                        import {
                            vpn
                        }
                        rd {
                            vpn {
                                export "10.0.0.2:101"
                            }
                        }
                        redistribute {
                            connected
                        }
                        route-target {
                            vpn {
                                export "65000:101"
                                import "65000:101"
                            }
                        }
                    }
                }
                neighbor fd01:101:12::1 {
                    peer-group "CE"
                }
                parameters {
                    router-id "10.0.0.2"
                }
                peer-group CE {
                    address-family {
                        ipv4-unicast
                        ipv6-unicast
                    }
                    remote-as "external"
                }
                sid {
                    vpn {
                        per-vrf {
                            export "101"
                        }
                    }
                }
                system-as "65000"
            }
        }
        table "101"
    }
}


// Warning: Do not remove the following line.
// vyos-config-version: "bgp@4:broadcast-relay@1:cluster@2:config-management@1:conntrack@5:conntrack-sync@2:container@1:dhcp-relay@2:dhcp-server"
// Release version: 1.5-rolling-202401310023