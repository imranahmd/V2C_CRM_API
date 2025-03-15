<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "w3.org/TR/REC-html40/loose.dtd">
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<style>
			body{
				padding: 0;
				margin: 0;
			}
			.header{
				background-color: #1b54dd;
				display: flex;
				justify-content: center;
				text-align: center;
				padding: 30px;
			}
			.header label{
				text-transform: uppercase;
				color: white;
				font-size: 30px;
				font-weight: 400 !important;
			}
			.content{
				display: flex;
				flex-direction: column;
			}
			.bordered-div{
				display: block;
    			margin: 0 auto;
				/*outline: #1b54dd solid 3px;
				outline-offset: 25px;*/
				border: #1b54dd solid 3px;
				padding:25px;
				width: calc(50% - 100px);
			}
			.value{
				color: black;
			}
			.pb10{
				padding-bottom: 10px;
			}
			.w50{
				width: 50%;
			}
			.w100{
				width: 100%;
			}
			.d-flex{
				display: flex;
			}
			
			.payment-btn:hover{
				background-color: #4070e0;
			}/*
			.payment-btn-div a{
				align-items: center;
					background-clip: padding-box;
					background-color: #1b54dd;
					border: 1px solid transparent;
					border-radius: 0.5rem;
					box-shadow: rgb(0 0 0 / 2%) 0 1px 3px 0;
					box-sizing: border-box;
					color: #fff;
					cursor: pointer;
					display: inline-flex;
					font-size: 16px;
					font-weight: 600;
					justify-content: center;
					line-height: 1.25;
					margin: 0;
					min-height: 3rem;
					padding: calc(0.875rem - 1px) calc(1.5rem - 1px);
					position: relative;
					text-decoration: none;
					transition: all 250ms;
					user-select: none;
					-webkit-user-select: none;
					touch-action: manipulation;
					vertical-align: baseline;
					width: auto;
					-webkit-transition: background-color 0.5s ease-out;
					-moz-transition: background-color 0.5s ease-out;
					-o-transition: background-color 0.5s ease-out;
					transition: background-color 0.5s ease-out;
			}*/
			.mobile-tr{
					display: none;
				}
			.label-td{
				width: 35%;
			}
			td label{
				word-break: keep-all;
			}
			@media only screen and (max-width: 1000px){
  				.table-div {
    				width: 100% !important;
  				}
				.desktop-tr td label{
					font-size: 12px !important;
				}
				.query-text{
					font-size: 14px;
				}
				.help-div a{
					font-size: 14px;
				}
			}
			@media only screen and (max-width: 780px) {
  				.table-div {
    				width: 100% !important;
  				}
				  .mer-name{
					font-size: 25px !important;
				}
				.amount-text{
					font-size: 22px !important;
				}
				.amount-digit{
					font-size: 14px !important;
				}
				.query-text{
					font-size: 12px;
				}
				.help-div a{
					font-size: 12px;
				}

			}
			@media only screen and (max-width: 550px){
				.mer-name{
					font-size: 25px !important;
				}
				.amount-text{
					font-size: 22px !important;
				}
				.amount-digit{
					font-size: 14px !important;
				}
				.main-div{
					width: 100% !important;
					padding: 0 !important;
				}
				
			}
			@media only screen and (max-width: 490px) {
  				.table-div {
    				width: 98% !important;
  				}
				.payment-btn-div a{
					padding:calc(0.875rem - 1px) calc(1.5rem - 1px) !important;
				}
				.desktop-tr{
					display: none;
				}
				.mobile-tr{
					display: block;
				}
			}
			@media only screen and (max-width: 330px) {
  				.table-div {
    				width: 100% !important;
  				}
				.payment-btn-div a{
					padding:calc(0.875rem - 1px) calc(1.5rem - 1px) !important;
				}
			}
		</style>
	</head>
	<body style="font-family: system-ui,-apple-system,system-ui,'Helvetica Neue',Helvetica,Arial,sans-serif;font-weight: 500;">
		
		<div class="main-div" style="background-color: #f1f1f1;padding: 80px;width: 45%;">
			<div style="background-color: #1b54dd;text-align: center;padding: 30px;text-align: center;">
				<label class="mer-name" style="text-transform: uppercase;color: white;font-size: 30px;font-weight: 400 !important;">
					${merName}</label>
			</div>
			<div>
				<div style="text-align: center;padding: 25px 0 30px 0;background-color: white;">
					<div class="amount-text" style="color: black;font-weight: 600;font-size: 32px;">&#8377;${amount}</div>
					<div class="amount-digit" style="color: #808893;font-size: 18px;">Amount payable</div>
				</div>
				<div style="background-color: white;padding-bottom: 1px;">
					<div style="width:calc(95% - 100px);margin: 0 auto;background-color: white;" class="table-div">
						<table style="border: #1b54dd solid 3px;width:100%;">
							<tbody>
								<tr class="desktop-tr">
									<td class="label-td" style="padding: 20px 0 10px 0;vertical-align: top;">
										<label  style="margin-left: 20px;color: #747d8a;display: inline-block;">Name</label>
									</td>
									<td style="padding: 20px 0 10px 0;">
										<label style="color:black;font-weight: 500;">${custName}</label>
									</td>
								</tr>
								<tr class="desktop-tr">
									<td class="label-td" style="padding: 10px 0 10px 0;vertical-align: top;">
										<label  style="margin-left: 20px;color: #747d8a;display: inline-block;">Remarks</label>
									</td>
									<td style="padding: 10px 0 10px 0;color:black;font-weight: 500;
									word-wrap: break-word;word-break: break-all;text-align-last: left;">
										${remarks}
									</td>
								</tr>
								<tr class="desktop-tr">
									<td class="label-td" style="padding: 10px 0 10px 0;">
										<label  style="margin-left: 20px;color: #747d8a;display: inline-block;">Invoice No.</label>
									</td>
									<td style="padding: 10px 0 10px 0;">
										<label style="color:black;font-weight: 500;">${invoiceNo}</label>
									</td>
								</tr>
								<tr class="desktop-tr">
									<td class="label-td" style="padding: 10px 0 20px 0;">
										<label  style="margin-left: 20px;color: #747d8a;display: inline-block;">Expiry Date</label>
									</td>
									<td style="padding: 10px 0 20px 0;">
										<label style="color:black;font-weight: 500;">${expiryDate}</label>
									</td>
								</tr>

								<!--mobile td-->
								<tr class="mobile-tr">
									<td style="padding: 20px 0 10px 0;">
										<label  style="margin-left: 20px;color: #747d8a;display: inline-block;">Name</label>
									</td>
								</tr>
								<tr class="mobile-tr">
									<td style="padding: 0px 0 10px 0;">
										<label style="color:black;margin-left: 20px;font-weight: 500;">${custName}</label>
									</td>
								</tr>
								<tr class="mobile-tr">
									<td style="padding: 10px 0 10px 0;">
										<label  style="margin-left: 20px;color: #747d8a;display: inline-block;">Remarks</label>
									</td>
								</tr>
								<tr class="mobile-tr">
									<td style="padding: 0px 0 10px 0;color:black;margin-left: 20px;font-weight: 500;
									word-wrap: break-word;word-break: keep-all;text-align-last: left;padding-left: 20px;">
										${remarks}
									</td>
								</tr>
								<tr class="mobile-tr">
									<td style="padding: 10px 0 10px 0;">
										<label  style="margin-left: 20px;color: #747d8a;display: inline-block;">Invoice No.</label>
									</td>
								</tr>
								<tr class="mobile-tr">
									<td style="padding: 0px 0 10px 0;">
										<label style="color:black;margin-left: 20px;font-weight: 500;">${invoiceNo}</label>
									</td>
								</tr>
								<tr class="mobile-tr">
									<td style="padding: 10px 0 10px 0;">
										<label  style="margin-left: 20px;color: #747d8a;display: inline-block;">Expiry Date</label>
									</td>
								</tr>
								<tr class="mobile-tr">
									<td style="padding: 0px 0 20px 0;">
										<label style="color:black;margin-left: 20px;font-weight: 500;">${expiryDate}</label>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="payment-btn-div" style="padding-top: 50px;display:block;margin-bottom:50px;text-align:center;">
						<a style="align-items: center;
						background-color: #1b54dd;
						border: 1px solid transparent;
						border-radius: 0.5rem;
						color: #fff;
						cursor: pointer;
						font-size: 16px;
						font-weight: 400;
						line-height: 1.25;
						margin: 0;
						min-height: 3rem;
						padding:calc(0.875rem - 1px) calc(1.5rem - 1px);
						text-decoration: none;
						vertical-align: baseline;
						width: auto;
						-webkit-transition: background-color 0.5s ease-out;
						-moz-transition: background-color 0.5s ease-out;
						-o-transition: background-color 0.5s ease-out;
						transition: background-color 0.5s ease-out;" href="${paymentUrl}" target="_blank" rel="noopener noreferrer">PROCEED TO PAY</a>
					</div>
				</div>
				
				<div style="background-color: #e3e1da;width: 100%;margin-top: 50px;">
					<div class="social-links-div" style="text-align:center;padding-top:15px;">
						<img src="${twitterLogo}" style="padding-right: 15px;" alt="twitter">
						<img src="${linkedInLogo}" alt="linkedin">
					</div>
					<div class="help-div" style="text-align: center;
					/* padding: 10px 0 10px 0; */"> 
						<div class="query-text" style="color: black">For any transaction related queries, please reach out to us at</div>
						<a style="text-decoration: none;color:#3290d3;font-weight:500;" class="testpay-support-mail" href="mailto:pg.support@testpay.in">pg.support@testpay.in</a>
					</div>
					<div class="footer-logo-div" style="padding: 10px 0 10px 0;text-align: center;">
						<img src="${onePLogo}" alt="testpay">
					</div>
				</div>
			</div>
		</div>
		<span style="opacity: 0;position: absolute;z-index: 100;bottom: 0;"> ${timeStamp} </span>
	</body>
</html>