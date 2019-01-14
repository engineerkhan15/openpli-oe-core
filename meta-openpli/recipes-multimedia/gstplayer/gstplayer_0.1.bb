DESCRIPTION = "gstplayer by samsamsam"
SECTION = "multimedia"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base"

inherit pkgconfig

SRC_URI = "https://gitlab.com/samsamsam/iptvplayer-bin-components.git;protocol=http"
SRC_URI =+ "file://0001-set-iptv-download-timeout-0-to-disable-ifdsrc.patch \
            file://0004-rename-stored-sink-to-dvbSink-for-clarity.patch \
            file://0009-try-to-get-PTS-from-video-sink-first.patch \
            file://0011-increase-eos-fix-timeout-to-10s.patch"

SRC_URI[md5sum] = "8e2ddfe70af0fa621f65fa853df07045"
SRC_URI[sha256sum] = "36d44390f737a1a387c21a7ab2f7519b0f50b4d649e26cc9c92a242781d7362c"

S = "${WORKDIR}/git/"

do_compile() {
    cd ${S}/gst-1.0
    ${CC} *.c ../common/*.c -I../common/ `pkg-config --cflags --libs gstreamer-1.0 gstreamer-pbutils-1.0` -o gstplayer_gst-1.0 ${LDFLAGS}
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/gst-1.0/gstplayer_gst-1.0 ${D}${bindir}/gstplayer
}

pkg_postinst_${PN}() {
    ln -sf gstplayer ${bindir}/gstplayer_gst-1.0
}

pkg_prerm_${PN}() {
    rm -f ${bindir}/gstplayer_gst-1.0
}
