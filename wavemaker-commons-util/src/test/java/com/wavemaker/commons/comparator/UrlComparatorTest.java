/**
 * Copyright (C) 2020 WaveMaker, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.comparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


/**
 * UrlComparator Tester.
 *
 * @author Arjun Sahasranam
 * @version 1.0
 * @since <pre>Nov 16, 2015</pre>
 */
public class UrlComparatorTest {

    final Comparator<String> stringPatternComparator = new UrlStringComparator<>() {

        @Override
        public String getUrlPattern(final String s) {
            return s;
        }

    };
    private UrlComparator<String> stringUrlComparator = new UrlComparator<>() {
        @Override
        public String getUrlPattern(final String s) {
            return s;
        }
    };
    private List<String> list1 = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();

    UrlComparatorTest() {
        list1.add("index.html");
        list1.add("/pages/Main/**");
        list1.add("/pages/footer/**");
        list1.add("/services/hrdb/User/getEmployee");
        list1.add("/services/hrdb/User/");
        list1.add("/services/hrdb/User/**");
        list1.add("/services/hrdb/queryExecutor/**");
        list1.add("/services/hrdb/**");
        list1.add("/services/security/**");
        list1.add("/test/**");
        list1.add("/test1/services/**");
        list1.add("/**");

        list2.add("/app.variables.json");
        list2.add("/index.html");
        list2.add("/j_spring_security_check");
        list2.add("/pages/BidDetail/**");
        list2.add("/pages/BidsLeftNav/**");
        list2.add("/pages/Common/**");
        list2.add("/pages/Customers/**");
        list2.add("/pages/FindBuyLeads/**");
        list2.add("/pages/Home/**");
        list2.add("/pages/ImageTesting/**");
        list2.add("/pages/Login/**");
        list2.add("/pages/Main/**");
        list2.add("/pages/ManageBids/**");
        list2.add("/pages/ManageRFX/**");
        list2.add("/pages/ManageUsers/**");
        list2.add("/pages/Membership/**");
        list2.add("/pages/MessageingWidget/**");
        list2.add("/pages/MsgExpandedView/**");
        list2.add("/pages/MsgWidgetCompactView/**");
        list2.add("/pages/MultiAttrWidgetDefin/**");
        list2.add("/pages/MultiAttrWidgetMatch/**");
        list2.add("/pages/MyAccount/**");
        list2.add("/pages/MyAccountLeftNav/**");
        list2.add("/pages/ProfileBasic/**");
        list2.add("/pages/ProfileContacts/**");
        list2.add("/pages/ProfileImageDocs/**");
        list2.add("/pages/ProfileLocations/**");
        list2.add("/pages/ProfileProducts/**");
        list2.add("/pages/ProfileSource/**");
        list2.add("/pages/RFQDetail/**");
        list2.add("/pages/RFQList/**");
        list2.add("/pages/RFQReadOnlyView/**");
        list2.add("/pages/RFQReadonlyViewByURL/**");
        list2.add("/pages/RFQResponses/**");
        list2.add("/pages/RFQResposeDetail/**");
        list2.add("/pages/RFXLeftNav/**");
        list2.add("/pages/RFXNotes/**");
        list2.add("/pages/Referrals/**");
        list2.add("/pages/Registration/**");
        list2.add("/pages/Settings/**");
        list2.add("/pages/Suppliers/**");
        list2.add("/pages/Support/**");
        list2.add("/pages/UploadedImage/**");
        list2.add("/pages/UserProfile/**");
        list2.add("/pages/VerifyEmail/**");
        list2.add("/pages/_Home/**");
        list2.add("/pages/_Payments/**");
        list2.add("/pages/_companies/**");
        list2.add("/pages/_compbasicinfo/**");
        list2.add("/pages/_footer/**");
        list2.add("/pages/_header/**");
        list2.add("/pages/_topnav/**");
        list2.add("/pages/_transactions/**");
        list2.add("/pages/_tranxleftnav/**");
        list2.add("/pages/_users/**");
        list2.add("/pages/compProfileLeftNav/**");
        list2.add("/pages/companyProfilePublic/**");
        list2.add("/pages/footer/**");
        list2.add("/pages/footerPublic/**");
        list2.add("/pages/header/**");
        list2.add("/pages/leftnav/**");
        list2.add("/pages/messagesview/**");
        list2.add("/pages/p_footer/**");
        list2.add("/pages/p_header/**");
        list2.add("/pages/p_home/**");
        list2.add("/pages/p_topnav/**");
        list2.add("/pages/rightnav/**");
        list2.add("/pages/topnav/**");
        list2.add("/services/emailLinkVerification/**");
        list2.add("/services/rfxdb/Types/search");
        list2.add("/services/rfxdb/Types/");
        list2.add("/services/security/**");
        list2.add("/");
        list2.add("/**");

    }


    /**
     * Method: compare(final T o1, final T o2)
     */
    @Test(dataProvider = "listProvider")
    public void testCompare(List list1, List list2) throws Exception {
        list1.sort(stringUrlComparator);
        list1.sort(stringPatternComparator);
        assertEquals(this.list1, list1);

        list2.sort(stringUrlComparator);
        list2.sort(stringPatternComparator);
        assertEquals(this.list2, list2);
    }

    @DataProvider
    public Object[][] listProvider() {
        Object[][] object = new Object[1][2];
        List<String> list1 = new ArrayList<>();
        list1.add("/test/**");

        list1.add("/**");
        list1.add("/services/hrdb/User/getEmployee");
        list1.add("/pages/Main/**");
        list1.add("/test1/services/**");
        list1.add("/services/hrdb/**");
        list1.add("index.html");
        list1.add("/pages/footer/**");
        list1.add("/services/hrdb/User/**");
        list1.add("/services/hrdb/queryExecutor/**");
        list1.add("/services/security/**");
        list1.add("/services/hrdb/User/");
        object[0][0] = list1;

        List<String> list2 = new ArrayList<>();

        list2.add("/app.variables.json");
        list2.add("/index.html");
        list2.add("/j_spring_security_check");
        list2.add("/pages/BidDetail/**");
        list2.add("/pages/BidsLeftNav/**");
        list2.add("/pages/Common/**");
        list2.add("/pages/Customers/**");
        list2.add("/pages/FindBuyLeads/**");
        list2.add("/pages/Home/**");
        list2.add("/pages/ImageTesting/**");
        list2.add("/pages/Login/**");
        list2.add("/pages/Main/**");
        list2.add("/pages/ManageBids/**");
        list2.add("/pages/ManageRFX/**");
        list2.add("/pages/ManageUsers/**");
        list2.add("/pages/Membership/**");
        list2.add("/pages/MessageingWidget/**");
        list2.add("/pages/MsgExpandedView/**");
        list2.add("/pages/MsgWidgetCompactView/**");
        list2.add("/pages/MultiAttrWidgetDefin/**");
        list2.add("/pages/MultiAttrWidgetMatch/**");
        list2.add("/pages/MyAccount/**");
        list2.add("/pages/MyAccountLeftNav/**");
        list2.add("/pages/ProfileBasic/**");
        list2.add("/pages/ProfileContacts/**");
        list2.add("/pages/ProfileImageDocs/**");
        list2.add("/pages/ProfileLocations/**");
        list2.add("/pages/ProfileProducts/**");
        list2.add("/pages/ProfileSource/**");
        list2.add("/pages/RFQDetail/**");
        list2.add("/pages/RFQList/**");
        list2.add("/pages/RFQReadOnlyView/**");
        list2.add("/pages/RFQReadonlyViewByURL/**");
        list2.add("/pages/RFQResponses/**");
        list2.add("/pages/RFQResposeDetail/**");
        list2.add("/pages/RFXLeftNav/**");
        list2.add("/pages/RFXNotes/**");
        list2.add("/pages/Referrals/**");
        list2.add("/pages/_compbasicinfo/**");
        list2.add("/pages/_footer/**");
        list2.add("/pages/_header/**");
        list2.add("/pages/_topnav/**");
        list2.add("/pages/_transactions/**");
        list2.add("/pages/_tranxleftnav/**");
        list2.add("/pages/_users/**");
        list2.add("/pages/compProfileLeftNav/**");
        list2.add("/pages/companyProfilePublic/**");
        list2.add("/pages/footer/**");
        list2.add("/pages/footerPublic/**");
        list2.add("/pages/header/**");
        list2.add("/pages/leftnav/**");
        list2.add("/pages/Registration/**");
        list2.add("/pages/Settings/**");
        list2.add("/pages/Suppliers/**");
        list2.add("/pages/Support/**");
        list2.add("/pages/UploadedImage/**");
        list2.add("/pages/UserProfile/**");
        list2.add("/pages/VerifyEmail/**");
        list2.add("/pages/_Home/**");
        list2.add("/pages/_Payments/**");
        list2.add("/pages/_companies/**");
        list2.add("/pages/messagesview/**");
        list2.add("/pages/p_footer/**");
        list2.add("/pages/p_header/**");
        list2.add("/pages/p_home/**");
        list2.add("/pages/p_topnav/**");
        list2.add("/pages/topnav/**");
        list2.add("/pages/rightnav/**");
        list2.add("/");
        list2.add("/**");
        list2.add("/services/emailLinkVerification/**");
        list2.add("/services/rfxdb/Types/search");
        list2.add("/services/rfxdb/Types/");
        list2.add("/services/security/**");

        object[0][1] = list2;
        return object;
    }


}
